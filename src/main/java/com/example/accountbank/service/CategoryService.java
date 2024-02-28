package com.example.accountbank.service;

import com.example.accountbank.dto.CategoryDTO;
import com.example.accountbank.entity.CategoryEntity;
import com.example.accountbank.repository.CategoryRepository;
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
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        List<CategoryEntity> results = categoryRepository.findAll();
        return results.stream().map(entity -> modelMapper.map(entity, CategoryDTO.class)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        Optional<CategoryEntity> result = categoryRepository.findById(id);
        CategoryEntity category = result.orElseThrow(() -> new NoSuchElementException(NOT_FOUND_DATA_ERROR_MESSAGE));

        return modelMapper.map(category, CategoryDTO.class);
    }
}
