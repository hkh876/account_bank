package com.example.accountbank.service;

import com.example.accountbank.dto.MemberDTO;
import com.example.accountbank.entity.MemberEntity;
import com.example.accountbank.repository.MemberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static com.example.accountbank.constant.AccountBankConstants.NOT_FOUND_DATA_ERROR_MESSAGE;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Autowired
    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Transactional(readOnly = true)
    public MemberDTO findByEmail(String email) {
        MemberEntity member = memberRepository.findByEmail(email).orElseThrow(() -> new NoSuchElementException(NOT_FOUND_DATA_ERROR_MESSAGE));
        return modelMapper.map(member, MemberDTO.class);
    }

    public void update(MemberDTO memberDTO) {
        MemberEntity member = memberRepository.findByEmail(memberDTO.getEmail())
                .orElseThrow(() -> new NoSuchElementException(NOT_FOUND_DATA_ERROR_MESSAGE));
        String password = passwordEncoder.encode(memberDTO.getPassword());

        member.changePassword(password);
        memberRepository.save(member);
    }
}
