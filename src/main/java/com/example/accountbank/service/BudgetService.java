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

import static com.example.accountbank.constant.AccountBankConstants.NOT_FOUND_DATA_ERROR_MESSAGE;

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

        return result.isPresent() ? modelMapper.map(result.get(), BudgetDTO.class) : null;
    }

    @Transactional(readOnly = true)
    public BudgetDTO findById(Long id) {
        Optional<BudgetEntity> result = budgetRepository.findById(id);
        return result.isPresent() ? modelMapper.map(result.get(), BudgetDTO.class) : null;
    }

    @Transactional
    public BudgetDTO register(BudgetDTO budgetDTO) {
        BudgetEntity entity = modelMapper.map(budgetDTO, BudgetEntity.class);
        BudgetEntity budget = budgetRepository.save(entity);

        return modelMapper.map(budget, BudgetDTO.class);
    }

    @Transactional
    public BudgetDTO update(BudgetDTO budgetDTO) {
        BudgetEntity entity = budgetRepository.findById(budgetDTO.getId()).orElseThrow(() -> new NoSuchElementException(NOT_FOUND_DATA_ERROR_MESSAGE));
        BudgetEntity newBudget = modelMapper.map(budgetDTO, BudgetEntity.class);

        entity.update(newBudget);
        BudgetEntity updatedEntity = budgetRepository.save(entity);

        return modelMapper.map(updatedEntity, BudgetDTO.class);
    }

    @Transactional
    public void deleteById(Long id) {
        budgetRepository.deleteById(id);
    }

    public int getMoneyByCategory(List<BudgetDTO> budgets, Long categoryId) {
        return budgets.stream()
                .filter(dto -> dto.getCategory().getId() == categoryId)
                .mapToInt(dto -> dto.getMoney())
                .findAny()
                .orElse(0);
    }

    public int getTotalMoney(List<BudgetDTO> budgets) {
        return budgets.stream().mapToInt(dto -> dto.getMoney()).sum();
    }
}
