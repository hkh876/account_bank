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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AccountBankController {
    private final AccountService accountService;
    private final TargetService targetService;
    private final CategoryService categoryService;
    private final BudgetService budgetService;
    private final DateService dateService;

    @Autowired
    public AccountBankController(
            AccountService accountService,
            TargetService targetService,
            CategoryService categoryService,
            BudgetService budgetService,
            DateService dateService)
    {
        this.accountService = accountService;
        this.targetService = targetService;
        this.categoryService = categoryService;
        this.budgetService = budgetService;
        this.dateService = dateService;
    }

    @GetMapping("/")
    public String indexView() {
        return "redirect:/account_bank/calendar";
    }

    @GetMapping("/account_bank/register")
    public String registerView(String date, Model model) {
        List<TargetDTO> targets = targetService.findAll();
        List<CategoryDTO> categories = categoryService.findAll();

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setDateStr(date);
        accountDTO.setDisplayDate(dateService.dateStrToDisplayFormat(date));

        model.addAttribute("account", accountDTO);
        model.addAttribute("targets", targets);
        model.addAttribute("categories", categories);

        return "contents/account_register";
    }

    @PostMapping("/account_bank/register")
    public String registerProcess(
            @Valid @ModelAttribute("account") AccountDTO accountDTO,
            BindingResult bindingResult,
            Model model) {
        if (accountDTO.getTargetId() == null) {
            bindingResult.addError(new FieldError("account", "targetId", "대상을 선택해 주세요."));
        }

        if (accountDTO.getCategoryId() == null) {
            bindingResult.addError(new FieldError("account", "categoryId", "카테고리를 선택해 주세요."));
        }

        if (bindingResult.hasErrors()) {
            List<TargetDTO> targets = targetService.findAll();
            List<CategoryDTO> categories = categoryService.findAll();

            model.addAttribute("targets", targets);
            model.addAttribute("categories", categories);

            return "contents/account_register";
        }

        accountDTO.setTargetDate(dateService.dateStrToLocalDateTime(accountDTO.getDateStr()));

        // 대상 설정
        TargetDTO target = targetService.findById(accountDTO.getTargetId());
        accountDTO.setTarget(target);

        // 카테고리 설정
        CategoryDTO category = categoryService.findById(accountDTO.getCategoryId());
        accountDTO.setCategory(category);

        // 데이터 등록
        // Fix me : 현재는 지출만
        int money = accountDTO.getMoney();
        accountDTO.setMoney(-1 * money);
        accountService.register(accountDTO);

        return "redirect:/account_bank/calendar";
    }

    @GetMapping("/account_bank/calendar")
    public String calendarView() {
        return "contents/calendar";
    }

    @GetMapping("/account_bank/detail")
    public String detailView(Long id, Model model) {
        List<TargetDTO> targets = targetService.findAll();
        List<CategoryDTO> categories = categoryService.findAll();

        AccountDTO account = accountService.findById(id);
        LocalDateTime targetDate = account.getTargetDate();
        account.setDisplayDate(dateService.dateTimeToDisplayFormat(targetDate));
        account.setDateStr(dateService.dateTimeToDateStr(targetDate));
        account.setMoney(Math.abs(account.getMoney()));
        account.setTargetId(account.getTarget().getId());
        account.setCategoryId(account.getCategory().getId());

        model.addAttribute("account", account);
        model.addAttribute("targets", targets);
        model.addAttribute("categories", categories);

        return "contents/account_detail";
    }

    @PostMapping("/account_bank/detail")
    public String updateProcess(
        @Valid @ModelAttribute("account") AccountDTO accountDTO,
        BindingResult bindingResult,
        Model model
    ) {
        if (accountDTO.getMoney() < 0) {
            bindingResult.addError(new FieldError("account", "money", "잘못된 값 입니다."));
        }

        if (accountDTO.getTargetId() == null) {
            bindingResult.addError(new FieldError("account", "targetId", "대상을 선택해 주세요."));
        }

        if (accountDTO.getCategoryId() == null) {
            bindingResult.addError(new FieldError("account", "categoryId", "카테고리를 선택해 주세요."));
        }

        if (bindingResult.hasErrors()) {
            List<TargetDTO> targets = targetService.findAll();
            List<CategoryDTO> categories = categoryService.findAll();

            model.addAttribute("targets", targets);
            model.addAttribute("categories", categories);

            return "contents/account_detail";
        }

        accountDTO.setTargetDate(dateService.dateStrToLocalDateTime(accountDTO.getDateStr()));

        // 대상 설정
        TargetDTO target = targetService.findById(accountDTO.getTargetId());
        accountDTO.setTarget(target);

        // 카테고리 설정
        CategoryDTO category = categoryService.findById(accountDTO.getCategoryId());
        accountDTO.setCategory(category);

        // 데이터 수정
        // Fix me : 현재는 지출만
        int money = accountDTO.getMoney();
        accountDTO.setMoney(-1 * money);

        accountService.update(accountDTO);
        return "redirect:/account_bank/calendar";
    }

    @GetMapping("/account_bank/delete")
    public String deleteProcess(Long id) {
        accountService.deleteById(id);
        return "redirect:/account_bank/calendar";
    }

    @GetMapping("/account_bank/settings")
    public String settingsView() {
        return "contents/settings";
    }

    @GetMapping("/account_bank/settings/budget")
    public String budgetView(Model model) {
        List<BudgetDTO> budgets = budgetService.findAll();

        // 총 예산
        int total = budgets.stream().mapToInt(BudgetDTO::getMoney).sum();

        model.addAttribute("budgets", budgets);
        model.addAttribute("total", total);

        return "contents/budget";
    }

    @GetMapping("/account_bank/settings/budget/register")
    public String budgetRegisterView(Model model) {
        List<CategoryDTO> categories = categoryService.findAll();

        model.addAttribute("budget", new BudgetDTO());
        model.addAttribute("categories", categories);

        return "contents/budget_register";
    }

    @PostMapping("/account_bank/settings/budget/register")
    public String budgetRegisterProcess(
        @Valid @ModelAttribute("budget") BudgetDTO budgetDTO,
        BindingResult bindingResult,
        Model model
    ) {
        Long categoryId = budgetDTO.getCategoryId();
        if (categoryId == null) {
            bindingResult.addError(new FieldError("budget", "categoryId", "카테고리를 선택해 주세요."));
        } else {
            // 카테고리 설정
            CategoryDTO category = categoryService.findById(categoryId);
            budgetDTO.setCategory(category);

            // 중복 체크
            BudgetDTO budget = budgetService.findByCategory(category);
            if (budget != null) {
                bindingResult.addError(new FieldError("budget", "duplicate", "이미 등록된 예산입니다."));
            }
        }

        if (bindingResult.hasErrors()) {
            List<CategoryDTO> categories = categoryService.findAll();

            model.addAttribute("categories", categories);
            return "contents/budget_register";
        }

        BudgetDTO newBudget = budgetService.register(budgetDTO);
        return "redirect:/account_bank/settings/budget";
    }

    @GetMapping("/account_bank/settings/budget/detail")
    public String budgetDetailView(Long id, Model model) {
        BudgetDTO budget = budgetService.findById(id);

        model.addAttribute("budget", budget);
        return "contents/budget_detail";
    }

    @PostMapping("/account_bank/settings/budget/detail")
    public String budgetUpdateProcess(BudgetDTO budgetDTO) {
        BudgetDTO budget = budgetService.update(budgetDTO);
        return "redirect:/account_bank/settings/budget";
    }

    @GetMapping("/account_bank/settings/budget/delete")
    public String budgetDeleteProcess(Long id) {
        budgetService.deleteById(id);
        return "redirect:/account_bank/settings/budget";
    }

    @GetMapping("/account_bank/settings/budget_history")
    public String budgetHistoryView(String date, Model model) {
        if (date == null) {
            LocalDateTime current = LocalDateTime.now();
            date = dateService.dateTimeToDateStr(current);
            model.addAttribute("displayDate", dateService.dateTimeToDisplayFormat(current));
        } else {
            model.addAttribute("displayDate", dateService.dateStrToDisplayFormat(date));
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

        categories.forEach(category -> {
            Long categoryId = category.getId();
            int totalMoney = accountService.getTotalMoneySameCategory(accounts, categoryId);

            if (totalMoney > 0) {
                BudgetHistoryDTO budgetHistory = new BudgetHistoryDTO();
                budgetHistory.setCategory(category);
                budgetHistory.setTotalMoney(totalMoney);
                budgetHistory.setBudgetMoney(budgetService.getBudgetMoneyByCategory(budgets, categoryId));

                budgetHistories.add(budgetHistory);
            }
        });

        model.addAttribute("budgetHistories", budgetHistories);
        return "contents/budget_history";
    }
}
