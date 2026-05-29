package com.unir.books.catalogue.utils;

import com.unir.books.catalogue.controller.model.BookDto;
import com.unir.books.catalogue.controller.model.GetBookResponseDto;
import com.unir.books.catalogue.repository.BookJpaRepository;
import com.unir.books.catalogue.repository.ImageJpaRepository;
import com.unir.books.catalogue.repository.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookMapper {

    private final BookJpaRepository bookJpaRepository;
    private final ImageJpaRepository imageJpaRepository;

    public List<BookDto> asBookDtoList(List<Book> books) {
        return books.stream()
                .map(book -> BookDto.builder()
                        .id(book.getId())
                        .title(book.getTitle())
                        .isbn(book.getIsbn())
                        .description(book.getDescription())
                        .valoracion(book.getValoracion())
                        .stock(book.getStock())
                        .price(book.getPrice())
                        .build())
                .toList();
    }

    public GetBookResponseDto asGetBookResponseDto(Book book) {
        return GetBookResponseDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .description(book.getDescription())
                .isbn(book.getIsbn())
                .valoracion(book.getValoracion())
                .stock(book.getStock())
                .price(book.getPrice())
                .author(book.getAuthor() != null ? book.getAuthor().getName() : null)
                .publisher(book.getPublisher().getName())
                .category(book.getCategory().getName())
                .images(book.getImageUrls())
                .build();
    }

    /*public Supply asSupply(Integer supplyId, WriteSupplyRequestDto supplyDto) {
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
    }*/
}
