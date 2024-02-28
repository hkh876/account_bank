package com.example.accountbank.controller;

import com.example.accountbank.dto.AccountDTO;
import com.example.accountbank.dto.CategoryDTO;
import com.example.accountbank.dto.TargetDTO;
import com.example.accountbank.enums.Division;
import com.example.accountbank.service.AccountService;
import com.example.accountbank.service.CategoryService;
import com.example.accountbank.service.DateService;
import com.example.accountbank.service.TargetService;
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
import java.util.List;

import static com.example.accountbank.constant.AccountBankConstants.*;

@Controller
public class AccountBankController {
    private final AccountService accountService;
    private final TargetService targetService;
    private final CategoryService categoryService;

    private final DateService dateService;

    @Autowired
    public AccountBankController(
            AccountService accountService,
            TargetService targetService,
            CategoryService categoryService,
            DateService dateService)
    {
        this.accountService = accountService;
        this.targetService = targetService;
        this.categoryService = categoryService;
        this.dateService = dateService;
    }

    @GetMapping("/")
    public String indexView() {
        return "redirect:" + ACCOUNT_BANK_CALENDAR_URL;
    }

    @GetMapping({"/account_bank/success", "/account_bank/error"})
    public String successOrErrorView(String message, String url, Model model) {
        model.addAttribute("message", message);
        model.addAttribute("url", url);

        return "contents/etc/success_or_error";
    }

    @GetMapping(ACCOUNT_BANK_REGISTER_URL)
    public String registerView(String date, Model model) {
        List<TargetDTO> targets = targetService.findAll();
        List<CategoryDTO> categories = categoryService.findAll();

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setDateStr(date);
        accountDTO.setDisplayDate(dateService.dateStrToDisplayFormat(date));

        model.addAttribute("account", accountDTO);
        model.addAttribute("targets", targets);
        model.addAttribute("categories", categories);

        return CONTENTS_ACCOUNT_BOOK_REGISTER_PATH;
    }

    @PostMapping(ACCOUNT_BANK_REGISTER_URL)
    public String registerProcess(
            @Valid @ModelAttribute("account") AccountDTO accountDTO,
            BindingResult bindingResult,
            Model model) {
        if (accountDTO.getTargetId() == null) {
            bindingResult.addError(new FieldError("account", "targetId", NOT_SELECTED_TARGET_ERROR_MESSAGE));
        }

        if (accountDTO.getCategoryId() == null) {
            bindingResult.addError(new FieldError("account", "categoryId", NOT_SELECTED_CATEGORY_ERROR_MESSAGE));
        }

        if (bindingResult.hasErrors()) {
            List<TargetDTO> targets = targetService.findAll();
            List<CategoryDTO> categories = categoryService.findAll();

            model.addAttribute("targets", targets);
            model.addAttribute("categories", categories);

            return CONTENTS_ACCOUNT_BOOK_REGISTER_PATH;
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
        accountDTO.setDivision(Division.EXPENSE);
        accountService.register(accountDTO);

        return "redirect:" + ACCOUNT_BANK_CALENDAR_URL;
    }

    @GetMapping(ACCOUNT_BANK_CALENDAR_URL)
    public String calendarView() {
        return CONTENTS_ACCOUNT_BOOK_CALENDAR_PATH;
    }

    @GetMapping(ACCOUNT_BANK_DETAIL_URL)
    public String detailView(Long id, Model model, RedirectAttributes redirectAttributes) {
        AccountDTO account = accountService.findById(id);
        if (account == null) {
            redirectAttributes.addAttribute("message", NOT_FOUND_DATA_ERROR_MESSAGE);
            redirectAttributes.addAttribute("url", ACCOUNT_BANK_CALENDAR_URL);

            return "redirect:/account_bank/error";
        }

        List<TargetDTO> targets = targetService.findAll();
        List<CategoryDTO> categories = categoryService.findAll();

        LocalDateTime targetDate = account.getTargetDate();
        account.setDisplayDate(dateService.dateTimeToDisplayFormat(targetDate));
        account.setDateStr(dateService.dateTimeToDateStr(targetDate));
        account.setMoney(Math.abs(account.getMoney()));
        account.setTargetId(account.getTarget().getId());
        account.setCategoryId(account.getCategory().getId());

        model.addAttribute("account", account);
        model.addAttribute("targets", targets);
        model.addAttribute("categories", categories);

        return CONTENTS_ACCOUNT_BOOK_DETAIL_PATH;
    }

    @PostMapping(ACCOUNT_BANK_DETAIL_URL)
    public String updateProcess(
        @Valid @ModelAttribute("account") AccountDTO accountDTO,
        BindingResult bindingResult,
        Model model)
    {
        if (accountDTO.getMoney() < 0) {
            bindingResult.addError(new FieldError("account", "money", INVALID_VALUE_ERROR_MESSAGE));
        }

        if (accountDTO.getTargetId() == null) {
            bindingResult.addError(new FieldError("account", "targetId", NOT_SELECTED_TARGET_ERROR_MESSAGE));
        }

        if (accountDTO.getCategoryId() == null) {
            bindingResult.addError(new FieldError("account", "categoryId", NOT_SELECTED_CATEGORY_ERROR_MESSAGE));
        }

        if (bindingResult.hasErrors()) {
            List<TargetDTO> targets = targetService.findAll();
            List<CategoryDTO> categories = categoryService.findAll();

            model.addAttribute("targets", targets);
            model.addAttribute("categories", categories);

            return CONTENTS_ACCOUNT_BOOK_DETAIL_PATH;
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
        accountDTO.setMoney(money);

        accountService.update(accountDTO);
        return "redirect:" + ACCOUNT_BANK_CALENDAR_URL;
    }

    @GetMapping(ACCOUNT_BANK_DELETE_URL)
    public String deleteProcess(Long id) {
        accountService.deleteById(id);
        return "redirect:" + ACCOUNT_BANK_CALENDAR_URL;
    }
}
