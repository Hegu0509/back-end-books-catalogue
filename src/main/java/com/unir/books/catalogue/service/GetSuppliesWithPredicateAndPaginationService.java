package com.unir.supplies.orders.service;

import com.unir.supplies.orders.controller.model.GetSuppliesResponseDto;
import com.unir.supplies.orders.repository.SupplyRepository;
import com.unir.supplies.orders.repository.model.Supply;
import com.unir.supplies.orders.utils.SupplyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetSuppliesWithPredicateAndPaginationService {

    private final SupplyRepository repository;
    private final SupplyMapper mapper;

    @Transactional(readOnly = true)
    public GetSuppliesResponseDto getSupplies(
            String name,
            String description,
            String fullDescription,
            String type,
            Double price,
            Integer stock,
            Integer pageSize,
            Integer page
    ) {

        List<Supply> supplies;
        if (StringUtils.hasLength(name) || StringUtils.hasLength(description) || StringUtils.hasLength(fullDescription)
                || StringUtils.hasLength(type)
                || price != null
                || stock != null) {
            supplies = repository.getSupplies(name, description, fullDescription, type, price, stock, pageSize, page);
        } else {
            supplies = repository.getSupplies(pageSize, page);
        }
        return GetSuppliesResponseDto.builder()
                .supplies(mapper.asSupplyDtoList(supplies))
                .build();
    }
}
