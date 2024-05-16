package com.example.accountbank.service;

import com.example.accountbank.dto.ShoppingDTO;
import com.example.accountbank.dto.ShoppingItemDTO;
import com.example.accountbank.dto.json.ShoppingItemJsonDTO;
import com.example.accountbank.dto.json.ShoppingJsonDTO;
import com.example.accountbank.entity.ShoppingEntity;
import com.example.accountbank.entity.ShoppingItemEntity;
import com.example.accountbank.repository.ShoppingItemRepository;
import com.example.accountbank.repository.ShoppingRepository;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static com.example.accountbank.constant.AccountBankConstants.NOT_FOUND_DATA_ERROR_MESSAGE;

@Log4j2
@Service
public class ShoppingService {
    private final ShoppingRepository shoppingRepository;
    private final ShoppingItemRepository shoppingItemRepository;
    private final ModelMapper modelMapper;

    public ShoppingService(ShoppingRepository shoppingRepository, ShoppingItemRepository shoppingItemRepository,
                           ModelMapper modelMapper)
    {
        this.shoppingRepository = shoppingRepository;
        this.shoppingItemRepository = shoppingItemRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional(readOnly = true)
    public List<ShoppingDTO> findAll() {
        List<ShoppingEntity> result = shoppingRepository.findAll(Sort.by(Sort.Direction.DESC, "updatedAt"));
        return result.stream().map(entity -> modelMapper.map(entity, ShoppingDTO.class)).toList();
    }

    @Transactional(readOnly = true)
    public ShoppingDTO findById(Long shoppingId) {
        ShoppingEntity result = shoppingRepository.findById(shoppingId)
                .orElseThrow(() -> new NoSuchElementException(NOT_FOUND_DATA_ERROR_MESSAGE));
        return modelMapper.map(result, ShoppingDTO.class);
    }

    @Transactional
    public void delete(Long shoppingId) {
        shoppingRepository.deleteById(shoppingId);
    }

    @Transactional
    public ShoppingDTO itemRegister(List<ShoppingItemJsonDTO> items) {
        ShoppingEntity entity = new ShoppingEntity();
        ShoppingEntity shopping = shoppingRepository.save(entity);

        List<ShoppingItemEntity> shoppingItems = items.stream().map(item -> {
            ShoppingItemDTO dto = modelMapper.map(item, ShoppingItemDTO.class);
            dto.setShopping(modelMapper.map(shopping, ShoppingDTO.class));
            return modelMapper.map(dto, ShoppingItemEntity.class);
        }).toList();
        shoppingItemRepository.saveAll(shoppingItems);

        return modelMapper.map(shopping, ShoppingDTO.class);
    }

    @Transactional
    public void itemUpdate(ShoppingJsonDTO shopping) {
        ShoppingEntity shoppingEntity = shoppingRepository.findById(shopping.getShoppingId())
                .orElseThrow(() -> new NoSuchElementException(NOT_FOUND_DATA_ERROR_MESSAGE));
        ShoppingDTO shoppingDTO = modelMapper.map(shoppingEntity, ShoppingDTO.class);

        ArrayList<ShoppingItemEntity> saveItems = new ArrayList<>();
        if (!shopping.getRegisterItems().isEmpty()) {
            // 새로 저장
            List<ShoppingItemEntity> items = shopping.getRegisterItems().stream().map(dto -> {
                ShoppingItemDTO item = modelMapper.map(dto, ShoppingItemDTO.class);
                item.setShopping(shoppingDTO);
                return modelMapper.map(item, ShoppingItemEntity.class);
            }).toList();
            saveItems.addAll(items);
        }

        if (!shopping.getUpdateItems().isEmpty()) {
            // 업데이트
            List<ShoppingItemEntity> items = shopping.getUpdateItems().stream().map(dto -> {
                ShoppingItemDTO item = modelMapper.map(dto, ShoppingItemDTO.class);
                item.setShopping(shoppingDTO);
                return modelMapper.map(item, ShoppingItemEntity.class);
            }).toList();
            saveItems.addAll(items);
        }

        // 생성 또는 업데이트
        if (!saveItems.isEmpty()) {
            shoppingItemRepository.saveAll(saveItems);
        }

        // 삭제
        if (!shopping.getDeleteIds().isEmpty()) {
            shoppingItemRepository.deleteAllById(shopping.getDeleteIds());
        }

        // 시간 업데이트
        shoppingEntity.update(LocalDateTime.now());
        shoppingRepository.save(shoppingEntity);
    }
}
