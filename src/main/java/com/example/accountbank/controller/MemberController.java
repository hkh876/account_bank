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

@Log4j2
@Controller
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/member/login")
    public String loginView(Principal principal) {
        if (principal != null) {
            return "redirect:/";
        }

        return "contents/login";
    }

    @GetMapping("/member/forget_password")
    public String forgetPasswordView() {
        return "contents/forget_password";
    }

    @PostMapping("/member/forget_password")
    public String forgetPasswordProcess(
        @Valid @ModelAttribute("member") MemberDTO memberDTO,
        BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "contents/forget_password";
        }

        memberService.update(memberDTO);
        return "redirect:/member/login";
    }
}
