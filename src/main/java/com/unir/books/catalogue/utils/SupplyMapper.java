package com.unir.supplies.orders.utils;

import com.unir.supplies.orders.controller.model.GetSupplyResponseDto;
import com.unir.supplies.orders.controller.model.SpecificationDto;
import com.unir.supplies.orders.controller.model.SupplyDto;
import com.unir.supplies.orders.controller.model.WriteSupplyRequestDto;
import com.unir.supplies.orders.exception.SupplyNotFoundException;
import com.unir.supplies.orders.repository.ImageJpaRepository;
import com.unir.supplies.orders.repository.SpecificationJpaRepository;
import com.unir.supplies.orders.repository.SupplyJpaRepository;
import com.unir.supplies.orders.repository.model.Supply;
import com.unir.supplies.orders.repository.model.SupplyImage;
import com.unir.supplies.orders.repository.model.SupplySpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SupplyMapper {

    private final SupplyJpaRepository supplyJpaRepository;
    private final SpecificationJpaRepository specificationJpaRepository;
    private final ImageJpaRepository imageJpaRepository;

    public List<SupplyDto> asSupplyDtoList(List<Supply> supplies) {
        return supplies.stream()
                .map(supply -> SupplyDto.builder()
                        .id(supply.getId())
                        .name(supply.getName())
                        .description(supply.getDescription())
                        .type(supply.getType())
                        .price(supply.getPrice().doubleValue())
                        .stock(supply.getStock())
                        .build())
                .toList();
    }

    public GetSupplyResponseDto asGetSupplyResponseDto(Supply supply) {
        return GetSupplyResponseDto.builder()
                .id(supply.getId())
                .name(supply.getName())
                .description(supply.getDescription())
                .fullDescription(supply.getFullDescription())
                .type(supply.getType())
                .price(supply.getPrice().doubleValue())
                .stock(supply.getStock())
                .specificationDtos(supply.getSpecificationsAsList())
                .images(supply.getImageUrls())
                .build();
    }

    public Supply asSupply(Integer supplyId, WriteSupplyRequestDto supplyDto) {
        Supply oldSupply = supplyJpaRepository.findById(supplyId).orElseThrow(
                () -> new SupplyNotFoundException("Supply with ID " + supplyId + " not found.")
        );
        return Supply.builder()
                .name(supplyDto.getName())
                .description(supplyDto.getDescription())
                .fullDescription(supplyDto.getFullDescription())
                .type(supplyDto.getType())
                .price(BigDecimal.valueOf(supplyDto.getPrice()))
                .stock(supplyDto.getStock())
                .specifications(getSpecificationsFromDto(oldSupply, supplyDto.getSpecificationDtos()))
                .images(getImagesFromDto(oldSupply, supplyDto.getImages()))
                .build();
    }

    public Supply asSupply(GetSupplyResponseDto getSupplyResponseDto) {
        Supply oldSupply = supplyJpaRepository.findById(getSupplyResponseDto.getId()).orElseThrow(
                () -> new SupplyNotFoundException("Supply with ID " + getSupplyResponseDto.getId() + " not found.")
        );
        return Supply.builder()
                .id(getSupplyResponseDto.getId())
                .name(getSupplyResponseDto.getName())
                .description(getSupplyResponseDto.getDescription())
                .fullDescription(getSupplyResponseDto.getFullDescription())
                .type(getSupplyResponseDto.getType())
                .price(getSupplyResponseDto.getPrice() != null ? BigDecimal.valueOf(getSupplyResponseDto.getPrice()) : null)
                .stock(getSupplyResponseDto.getStock())
                .specifications(getSpecificationsFromDto(oldSupply, getSupplyResponseDto.getSpecificationDtos()))
                .images(getImagesFromDto(oldSupply, getSupplyResponseDto.getImages()))
                .build();
    }

    private List<SupplySpecification> getSpecificationsFromDto(Supply oldSupply, List<SpecificationDto> specificationDtos) {
        specificationJpaRepository.deleteBySupplyId(oldSupply.getId());
        return specificationDtos.stream()
                .map(spec -> SupplySpecification.builder()
                        .supply(oldSupply)
                        .specKey(spec.getSpecKey())
                        .specValue(spec.getSpecValue())
                        .build())
                .toList();
    }

    private List<SupplyImage> getImagesFromDto(Supply oldSupply, List<String> images) {
        imageJpaRepository.deleteBySupplyId(oldSupply.getId());
        return images.stream()
                .map(imageUrl -> SupplyImage.builder()
                        .supply(oldSupply)
                        .imageUrl(imageUrl)
                        .build())
                .toList();
    }

    public List<SupplySpecification> mapSpecifications(Map<String, String> specifications) {
        return specifications.entrySet().stream()
                .map(entry -> SupplySpecification.builder()
                        .specKey(entry.getKey())
                        .specValue(entry.getValue())
                        .build())
                .toList();
    }

    public List<SupplyImage> mapImages(List<String> images) {
        return images.stream()
                .map(imageUrl -> SupplyImage.builder()
                        .imageUrl(imageUrl)
                        .build())
                .toList();
    }
}
