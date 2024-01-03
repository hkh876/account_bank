package com.example.accountbank.service;

import com.example.accountbank.dto.AccountDTO;
import com.example.accountbank.entity.AccountEntity;
import com.example.accountbank.repository.AccountRepository;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Log4j2
@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AccountService(AccountRepository accountRepository, ModelMapper modelMapper) {
        this.accountRepository = accountRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public AccountDTO register(AccountDTO accountDTO) {
        AccountEntity entity = modelMapper.map(accountDTO, AccountEntity.class);
        AccountEntity result = accountRepository.save(entity);

        return modelMapper.map(result, AccountDTO.class);
    }

    @Transactional(readOnly = true)
    public List<AccountDTO> findAllByTargetDateBetween(LocalDateTime start, LocalDateTime end) {
        List<AccountEntity> result = accountRepository.findAllByTargetDateBetween(start, end);
        return result.stream().map(entity -> modelMapper.map(entity, AccountDTO.class)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AccountDTO findById(Long id) {
        AccountEntity result = accountRepository.findById(id).orElseThrow(() -> new NoSuchElementException("데이터가 존재 하지 않습니다."));
        return modelMapper.map(result, AccountDTO.class);
    }

    @Transactional
    public AccountDTO update(AccountDTO accountDTO) {
        AccountEntity entity = accountRepository.findById(accountDTO.getId()).orElseThrow(() -> new NoSuchElementException("데이터가 존재 하지 않습니다."));
        AccountEntity newAccount = modelMapper.map(accountDTO, AccountEntity.class);

        entity.update(newAccount);
        AccountEntity updatedEntity = accountRepository.save(entity);

        return modelMapper.map(updatedEntity, AccountDTO.class);
    }

    @Transactional
    public void deleteById(Long id) {
        accountRepository.deleteById(id);
    }
}
