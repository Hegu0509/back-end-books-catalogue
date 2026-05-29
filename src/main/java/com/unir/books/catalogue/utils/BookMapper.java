package com.unir.books.catalogue.utils;

import com.unir.books.catalogue.controller.model.BookDto;
import com.unir.books.catalogue.controller.model.GetBookResponseDto;
import com.unir.books.catalogue.controller.model.ImageDto;
import com.unir.books.catalogue.controller.model.WriteBookRequestDto;
import com.unir.books.catalogue.exception.AuthorNotFoundException;
import com.unir.books.catalogue.exception.BookNotFoundException;
import com.unir.books.catalogue.exception.PublisherNotFoundException;
import com.unir.books.catalogue.repository.*;
import com.unir.books.catalogue.repository.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BookMapper {

    private final BookJpaRepository bookJpaRepository;
    private final AuthorJpaRepository authorJpaRepository;
    private final PublisherJpaRepository publisherJpaRepository;
    private  final CategoryJpaRepository categoryJpaRepository;
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

    public Book asBook(Long bookId, WriteBookRequestDto bookDto) {
        Book oldBook = bookJpaRepository.findById(bookId).orElseThrow(
                () -> new BookNotFoundException("Book with ID " + bookId + " not found.")
        );
        return Book.builder()
                .isbn(bookDto.getIsbn())
                .title(bookDto.getTitle())
                .description(bookDto.getDescription())
                .shortDescription(bookDto.getShortDescription())
                .publicationDate(bookDto.getPublicationDate())
                .edition(bookDto.getEdition())
                .language(bookDto.getLanguage())
                .format(bookDto.getFormat())
                .pages(bookDto.getPages())
                .price(bookDto.getPrice() != null ? bookDto.getPrice() : BigDecimal.ZERO)
                .stock(bookDto.getStock() != null ? bookDto.getStock() : 0)
                .author(getAuthorFromDto(bookDto.getAuthorId()))
                .publisher(getPublisherFromDto(bookDto.getPublisherId()))
                .category(getCategoryFromDto(bookDto.getCategoryId()))
                .images(getImagesFromDto(oldBook, bookDto.getImages()))
                .visible(oldBook.getVisible())
                .valoracion(oldBook.getValoracion())
                .createdAt(oldBook.getCreatedAt())
                .updatedAt(java.time.LocalDateTime.now())
                .build();
    }

    public Book asBook(GetBookResponseDto getBookResponseDto) {
        Book oldBook = bookJpaRepository.findById(getBookResponseDto.getId()).orElseThrow(
                () -> new BookNotFoundException("Supply with ID " + getBookResponseDto.getId() + " not found.")
        );
        return Book.builder()
                .id(getBookResponseDto.getId())
                .isbn(getBookResponseDto.getIsbn())
                .title(getBookResponseDto.getTitle())
                .description(getBookResponseDto.getDescription())
                .price(getBookResponseDto.getPrice() != null ? getBookResponseDto.getPrice() : BigDecimal.ZERO)
                .stock(getBookResponseDto.getStock() != null ? getBookResponseDto.getStock() : 0)
                .visible(oldBook.getVisible())
                .valoracion(oldBook.getValoracion())
                .createdAt(oldBook.getCreatedAt())
                .updatedAt(java.time.LocalDateTime.now())
                .build();
    }

    private Author getAuthorFromDto(Long authorId) {
        return authorJpaRepository.findById(authorId).orElseThrow(() -> new AuthorNotFoundException("Author not found with id: " + authorId));
    }

    private Publisher getPublisherFromDto(Long publisherId) {
        return publisherJpaRepository.findById(publisherId).orElseThrow(() -> new PublisherNotFoundException("Publisher not found with id: " + publisherId));
    }

    private Category getCategoryFromDto(Long categoryId) {
        return categoryJpaRepository.findById(categoryId).orElseThrow(() -> new AuthorNotFoundException("Category not found with id: " + categoryId));
    }

    private List<Image> getImagesFromDto(Book oldBook, List<ImageDto> imagesDto) {
        imageJpaRepository.deleteByBookId(oldBook.getId());
        List<Image> images = new ArrayList<>();
        imagesDto.forEach(image -> {
            Image imageModified = Image.builder()
                    .book(oldBook)
                    .imageUrl(image.getImageUrl() != null && !image.getImageUrl().isEmpty() ? image.getImageUrl() : "Sin imagen")
                    .imageType(image.getImageType() != null ? image.getImageType() : "cover")
                    .imageOrder(image.getImageOrder()!= null ? image.getImageOrder() : null)
                    .altText(image.getAltText() != null ? image.getAltText() : null)
                    .build();
            images.add(imageModified);
        });
        return images;
    }
}
