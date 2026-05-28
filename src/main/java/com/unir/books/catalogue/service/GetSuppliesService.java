package com.unir.supplies.orders.service;

import com.unir.supplies.orders.controller.model.GetSuppliesResponseDto;
import com.unir.supplies.orders.controller.model.GetSupplyResponseDto;
import com.unir.supplies.orders.exception.SupplyNotFoundException;
import com.unir.supplies.orders.repository.SupplyJpaRepository;
import com.unir.supplies.orders.repository.model.Supply;
import com.unir.supplies.orders.utils.SupplyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetSuppliesService {

    private final SupplyJpaRepository repository;
    private final SupplyMapper mapper;

    @Transactional(readOnly = true)
    public GetSuppliesResponseDto getSupplies() {
        List<Supply> supplies = repository.findAvailableSupplies();
        return GetSuppliesResponseDto.builder()
                .supplies(mapper.asSupplyDtoList(supplies))
                .build();
    }

    @Transactional(readOnly = true)
    public GetSupplyResponseDto getSupply(Integer supplyId) {
        Optional<Supply> supply = repository.findById(supplyId);
        return supply.map(
                s -> GetSupplyResponseDto.builder()
                        .id(s.getId())
                        .name(s.getName())
                        .description(s.getDescription())
                        .fullDescription(s.getFullDescription())
                        .type(s.getType())
                        .price(s.getPrice().doubleValue())
                        .stock(s.getStock())
                        .specificationDtos(s.getSpecificationsAsList())
                        .images(s.getImageUrls())
                        .build()
        ).orElseThrow(
                () -> new SupplyNotFoundException("Supply not found with id: " + supplyId));
    }
}
