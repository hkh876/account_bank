package com.example.accountbank.controller;

import com.example.accountbank.dto.AccountDTO;
import com.example.accountbank.dto.CategoryDTO;
import com.example.accountbank.dto.TargetDTO;
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

import java.time.LocalDateTime;
import java.util.List;

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
}
