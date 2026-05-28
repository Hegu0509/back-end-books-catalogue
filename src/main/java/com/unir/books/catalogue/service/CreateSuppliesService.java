package com.unir.supplies.orders.service;

import com.unir.supplies.orders.controller.model.WriteSupplyRequestDto;
import com.unir.supplies.orders.controller.model.GetSupplyResponseDto;
import com.unir.supplies.orders.repository.SupplyJpaRepository;
import com.unir.supplies.orders.repository.model.Supply;
import com.unir.supplies.orders.repository.model.SupplySpecification;
import com.unir.supplies.orders.repository.model.SupplyImage;
import com.unir.supplies.orders.utils.SupplyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CreateSuppliesService {

    private final SupplyJpaRepository supplyJpaRepository;
    private final SupplyMapper supplyMapper;

    @Transactional
    public GetSupplyResponseDto createSupply(WriteSupplyRequestDto request) {
        // Crear la entidad Supply principal
        Supply supply = Supply.builder()
                .name(request.getName())
                .description(request.getDescription())
                .fullDescription(request.getFullDescription())
                .type(request.getType())
                .price(request.getPrice() != null ? BigDecimal.valueOf(request.getPrice()) : null)
                .stock(request.getStock())
                .build();

        // Crear especificaciones si existen
        if (request.getSpecificationDtos() != null && !request.getSpecificationDtos().isEmpty()) {
            List<SupplySpecification> specifications = new ArrayList<>();
            request.getSpecificationDtos().forEach((specification) -> {
                SupplySpecification spec = SupplySpecification.builder()
                        .supply(supply)
                        .specKey(specification.getSpecKey())
                        .specValue(specification.getSpecValue())
                        .build();
                specifications.add(spec);
            });
            supply.setSpecifications(specifications);
        }

        // Crear imágenes si existen
        if (request.getImages() != null && !request.getImages().isEmpty()) {
            List<SupplyImage> images = new ArrayList<>();
            request.getImages().forEach(imageUrl -> {
                SupplyImage image = SupplyImage.builder()
                        .supply(supply)
                        .imageUrl(imageUrl)
                        .build();
                images.add(image);
            });
            supply.setImages(images);
        }

        // Guardar la entidad (cascade guardará automáticamente las especificaciones e imágenes)
        Supply savedSupply = supplyJpaRepository.save(supply);
        return supplyMapper.asGetSupplyResponseDto(savedSupply);
    }
}
