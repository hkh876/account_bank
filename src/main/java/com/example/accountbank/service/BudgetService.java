package com.example.accountbank.service;

import com.example.accountbank.dto.BudgetDTO;
import com.example.accountbank.dto.CategoryDTO;
import com.example.accountbank.entity.BudgetEntity;
import com.example.accountbank.entity.CategoryEntity;
import com.example.accountbank.repository.BudgetRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BudgetService {
    private final BudgetRepository budgetRepository;
    private final ModelMapper modelMapper;

    public BudgetService(BudgetRepository budgetRepository, ModelMapper modelMapper) {
        this.budgetRepository = budgetRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional(readOnly = true)
    public List<BudgetDTO> findAll() {
        List<BudgetEntity> result = budgetRepository.findAll();
        return result.stream().map(entity -> modelMapper.map(entity, BudgetDTO.class)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BudgetDTO findByCategory(CategoryDTO categoryDTO) {
        CategoryEntity categoryEntity = modelMapper.map(categoryDTO, CategoryEntity.class);
        Optional<BudgetEntity> result = budgetRepository.findByCategory(categoryEntity);

        if (!result.isPresent()) {
            return null;
        }

        return modelMapper.map(result.get(), BudgetDTO.class);
    }

    @Transactional(readOnly = true)
    public BudgetDTO findById(Long id) {
        BudgetEntity result = budgetRepository.findById(id).orElseThrow(() -> new NoSuchElementException("데이터가 존재 하지 않습니다."));
        return modelMapper.map(result, BudgetDTO.class);
    }

    @Transactional
    public BudgetDTO register(BudgetDTO budgetDTO) {
        BudgetEntity entity = modelMapper.map(budgetDTO, BudgetEntity.class);
        BudgetEntity budget = budgetRepository.save(entity);

        return modelMapper.map(budget, BudgetDTO.class);
    }

    @Transactional
    public BudgetDTO update(BudgetDTO budgetDTO) {
        BudgetEntity entity = budgetRepository.findById(budgetDTO.getId()).orElseThrow(() -> new NoSuchElementException("데이터가 존재 하지 않습니다."));
        BudgetEntity newBudget = modelMapper.map(budgetDTO, BudgetEntity.class);

        entity.update(newBudget);
        BudgetEntity updatedEntity = budgetRepository.save(entity);

        return modelMapper.map(updatedEntity, BudgetDTO.class);
    }

    @Transactional
    public void deleteById(Long id) {
        budgetRepository.deleteById(id);
    }

    public int getBudgetMoneyByCategory(List<BudgetDTO> budgets, Long categoryId) {
        return budgets.stream()
                .filter(dto -> dto.getCategory().getId() == categoryId)
                .mapToInt(dto -> dto.getMoney())
                .findAny()
                .orElse(0);
    }
}
