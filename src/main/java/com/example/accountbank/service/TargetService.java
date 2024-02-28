package com.example.accountbank.service;

import com.example.accountbank.dto.TargetDTO;
import com.example.accountbank.entity.TargetEntity;
import com.example.accountbank.repository.TargetRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.accountbank.constant.AccountBankConstants.NOT_FOUND_DATA_ERROR_MESSAGE;

@Service
public class TargetService {
    private final TargetRepository targetRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TargetService(TargetRepository targetRepository, ModelMapper modelMapper) {
        this.targetRepository = targetRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional(readOnly = true)
    public List<TargetDTO> findAll() {
        List<TargetEntity> results = targetRepository.findAll();
        return results.stream().map(entity -> modelMapper.map(entity, TargetDTO.class)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TargetDTO findById(Long id) {
        Optional<TargetEntity> result =  targetRepository.findById(id);
        TargetEntity target = result.orElseThrow(() -> new NoSuchElementException(NOT_FOUND_DATA_ERROR_MESSAGE));

        return modelMapper.map(target, TargetDTO.class);
    }
}
