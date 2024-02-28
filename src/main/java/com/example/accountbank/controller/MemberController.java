package com.example.accountbank.controller;

import com.example.accountbank.dto.MemberDTO;
import com.example.accountbank.service.MemberService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

import static com.example.accountbank.constant.AccountBankConstants.*;

@Log4j2
@Controller
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping(MEMBER_LOGIN_URL)
    public String loginView(Principal principal) {
        if (principal != null) {
            return "redirect:/";
        }

        return CONTENTS_MEMBER_LOGIN_PATH;
    }

    @GetMapping(MEMBER_FORGET_PASSWORD_URL)
    public String forgetPasswordView() {
        return CONTENTS_MEMBER_FORGET_PASSWORD_PATH;
    }

    @PostMapping(MEMBER_FORGET_PASSWORD_URL)
    public String forgetPasswordProcess(
        @Valid @ModelAttribute("member") MemberDTO memberDTO,
        BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return CONTENTS_MEMBER_FORGET_PASSWORD_PATH;
        }

        memberService.update(memberDTO);
        return "redirect:" + MEMBER_LOGIN_URL;
    }
}
