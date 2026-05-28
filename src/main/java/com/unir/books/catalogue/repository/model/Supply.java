package com.unir.supplies.orders.repository.model;

import com.unir.supplies.orders.controller.model.SpecificationDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.*;

@Entity
@Table(name = "supply")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Supply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "full_description", columnDefinition = "TEXT")
    private String fullDescription;

    @Column(name = "type", length = 100)
    private String type;

    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "stock")
    private Integer stock;

    @OneToMany(mappedBy = "supply", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SupplySpecification> specifications;

    @OneToMany(mappedBy = "supply", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SupplyImage> images;

    public List<SpecificationDto> getSpecificationsAsList() {
        if (specifications == null) {
            return Collections.emptyList();
        }
        return specifications.stream()
                .map(spec -> SpecificationDto
                        .builder()
                        .specKey(spec.getSpecKey())
                        .specValue(spec.getSpecValue()).build())
                .toList();
    }

    public List<String> getImageUrls() {
        if (images == null) {
            return new ArrayList<>();
        }
        return images.stream()
                .map(SupplyImage::getImageUrl)
                .collect(java.util.stream.Collectors.toList());
    }
}
