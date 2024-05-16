package com.example.accountbank.controller;

import com.example.accountbank.dto.*;
import com.example.accountbank.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.example.accountbank.constant.AccountBankConstants.*;

@Controller
public class SettingsController {
    private final BudgetService budgetService;
    private final CategoryService categoryService;
    private final AccountService accountService;
    private final DateService dateService;
    private final ShoppingService shoppingService;

    @Autowired
    public SettingsController(
            BudgetService budgetService,
            CategoryService categoryService,
            AccountService accountService,
            DateService dateService,
            ShoppingService shoppingService)
    {
        this.budgetService = budgetService;
        this.categoryService = categoryService;
        this.accountService = accountService;
        this.shoppingService = shoppingService;
        this.dateService = dateService;
    }

    @GetMapping(SETTINGS_URL)
    public String settingsView() {
        return CONTENTS_SETTINGS_LIST_PATH;
    }

    @GetMapping(SETTINGS_BUDGET_URL)
    public String budgetView(Model model) {
        List<BudgetDTO> budgets = budgetService.findAll();

        // 총 예산
        int total = budgets.stream().mapToInt(BudgetDTO::getMoney).sum();

        model.addAttribute("budgets", budgets);
        model.addAttribute("total", total);

        return CONTENTS_BUDGET_LIST_PATH;
    }

    @GetMapping(SETTINGS_BUDGET_REGISTER_URL)
    public String budgetRegisterView(Model model) {
        List<CategoryDTO> categories = categoryService.findAll();

        model.addAttribute("budget", new BudgetDTO());
        model.addAttribute("categories", categories);

        return CONTENTS_BUDGET_REGISTER_PATH;
    }

    @PostMapping(SETTINGS_BUDGET_REGISTER_URL)
    public String budgetRegisterProcess(
            @Valid @ModelAttribute("budget") BudgetDTO budgetDTO,
            BindingResult bindingResult,
            Model model)
    {
        Long categoryId = budgetDTO.getCategoryId();
        if (categoryId == null) {
            bindingResult.addError(new FieldError("budget", "categoryId", NOT_SELECTED_CATEGORY_ERROR_MESSAGE));
        } else {
            // 카테고리 설정
            CategoryDTO category = categoryService.findById(categoryId);
            budgetDTO.setCategory(category);

            // 중복 체크
            BudgetDTO budget = budgetService.findByCategory(category);
            if (budget != null) {
                bindingResult.addError(new FieldError("budget", "duplicate", DUPLICATED_BUDGET_ERROR_MESSAGE));
            }
        }

        if (bindingResult.hasErrors()) {
            List<CategoryDTO> categories = categoryService.findAll();

            model.addAttribute("categories", categories);
            return CONTENTS_BUDGET_REGISTER_PATH;
        }

        BudgetDTO newBudget = budgetService.register(budgetDTO);
        return "redirect:" + SETTINGS_BUDGET_URL;
    }

    @GetMapping(SETTINGS_BUDGET_DETAIL_URL)
    public String budgetDetailView(Long id, Model model, RedirectAttributes redirectAttributes) {
        BudgetDTO budget = budgetService.findById(id);
        if (budget == null) {
            redirectAttributes.addAttribute("message", NOT_FOUND_DATA_ERROR_MESSAGE);
            redirectAttributes.addAttribute("url", SETTINGS_BUDGET_URL);

            return "redirect:/account_bank/error";
        }

        model.addAttribute("budget", budget);
        return CONTENTS_BUDGET_DETAIL_PATH;
    }

    @PostMapping(SETTINGS_BUDGET_DETAIL_URL)
    public String budgetUpdateProcess(BudgetDTO budgetDTO) {
        BudgetDTO budget = budgetService.update(budgetDTO);
        return "redirect:" + SETTINGS_BUDGET_URL;
    }

    @GetMapping(SETTINGS_BUDGET_DELETE_URL)
    public String budgetDeleteProcess(Long id) {
        budgetService.deleteById(id);
        return "redirect:" + SETTINGS_BUDGET_URL;
    }

    @GetMapping(SETTINGS_BUDGET_HISTORY_URL)
    public String budgetHistoryView(String date, Model model) {
        if (date == null) {
            LocalDateTime current = LocalDateTime.now();
            date = dateService.dateTimeToDateStr(current);
            model.addAttribute("displayDate", dateService.dateTimeToDisplayFormat(current));
            model.addAttribute("dateStr", dateService.dateTimeToDateStr(current));
        } else {
            model.addAttribute("displayDate", dateService.dateStrToDisplayFormat(date));
            model.addAttribute("dateStr", date);
        }

        LocalDateTime startDate = dateService.getStartDateOfMonth(date);
        LocalDateTime endDate = dateService.getEndDateOfMonth(date);
        ArrayList<BudgetHistoryDTO> budgetHistories = new ArrayList<>();

        // 가계부 조회
        List<AccountDTO> accounts = accountService.findAllByTargetDateBetween(startDate, endDate);

        // 카테고리 목록
        List<CategoryDTO> categories = categoryService.findAll();

        // 예산 목록
        List<BudgetDTO> budgets = budgetService.findAll();

        // 전체
        AtomicInteger accountTotalMoney = new AtomicInteger();
        int budgetTotalMoney = budgetService.getTotalMoney(budgets);

        categories.forEach(category -> {
            Long categoryId = category.getId();
            int totalMoney = accountService.getTotalMoneySameCategory(accounts, categoryId);

            if (totalMoney > 0) {
                BudgetHistoryDTO budgetHistory = new BudgetHistoryDTO();
                budgetHistory.setCategory(category);
                budgetHistory.setTotalMoney(totalMoney);
                budgetHistory.setBudgetMoney(budgetService.getMoneyByCategory(budgets, categoryId));

                budgetHistories.add(budgetHistory);
                accountTotalMoney.addAndGet(totalMoney);
            }
        });

        model.addAttribute("budgetHistories", budgetHistories);
        model.addAttribute("accountTotalMoney", accountTotalMoney);
        model.addAttribute("budgetTotalMoney", budgetTotalMoney);

        return CONTENTS_BUDGET_HISTORY_LIST_PATH;
    }

    @GetMapping(SETTINGS_BUDGET_HISTORY_DETAIL_URL)
    public String budgetHistoryDetailView(Long categoryId, String date, Model model) {
        // 카테고리 조회
        CategoryDTO category = categoryService.findById(categoryId);

        // 예산 조회
        BudgetDTO budget = budgetService.findByCategory(category);

        // 날짜
        LocalDateTime start = dateService.getStartDateOfMonth(date);
        LocalDateTime end = dateService.getEndDateOfMonth(date);

        List<AccountDTO> accounts = accountService.findAllByCategoryAndTargetDateBetween(category, start, end);
        int totalMoney = accountService.getTotalMoneySameCategory(accounts, categoryId);

        model.addAttribute("accounts", accounts);
        model.addAttribute("category", category);
        model.addAttribute("budget", budget);
        model.addAttribute("totalMoney", totalMoney);

        return CONTENTS_BUDGET_HISTORY_DETAIL_PATH;
    }

    @GetMapping(SETTINGS_GROCERY_SHOPPING_URL)
    public String groceryShoppingView(Model model) {
        List<ShoppingDTO> shoppings = shoppingService.findAll();

        model.addAttribute("shoppings", shoppings);
        return CONTENTS_GROCERY_SHOPPING_LIST_PATH;
    }

    @GetMapping(SETTINGS_GROCERY_SHOPPING_REGISTER_URL)
    public String groceryShoppingRegisterView() {
        return CONTENTS_GROCERY_SHOPPING_REGISTER_PATH;
    }

    @GetMapping(SETTINGS_GROCERY_SHOPPING_DETAIL_URL)
    public String groceryShoppingDetailView(Long id, Model model) {
        ShoppingDTO shopping = shoppingService.findById(id);

        model.addAttribute("shopping", shopping);
        return CONTENTS_GROCERY_SHOPPING_DETAIL_PATH;
    }
}
