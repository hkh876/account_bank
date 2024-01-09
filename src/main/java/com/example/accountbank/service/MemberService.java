package com.example.accountbank.service;

import com.example.accountbank.dto.MemberDTO;
import com.example.accountbank.entity.MemberEntity;
import com.example.accountbank.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void update(MemberDTO memberDTO) {
        MemberEntity member = memberRepository.findByEmail(memberDTO.getEmail())
                .orElseThrow(() -> new NoSuchElementException("데이터가 존재 하지 않습니다."));
        String password = passwordEncoder.encode(memberDTO.getPassword());

        member.changePassword(password);
        memberRepository.save(member);
    }
}
