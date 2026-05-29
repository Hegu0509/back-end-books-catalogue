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
                .publisher(book.getPublisher().getName() != null ? book.getPublisher().getName() : null)
                .category(book.getCategory().getName())
                .images(book.getImageUrls())
                .build();
    }

    public WriteBookRequestDto asWriteBookRequestDto(Book book) {
        return WriteBookRequestDto.builder()
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .description(book.getDescription())
                .shortDescription(book.getShortDescription())
                .authorId(book.getAuthor() != null ? book.getAuthor().getId() : null)
                .publisherId(book.getPublisher() != null ? book.getPublisher().getId() : null)
                .categoryId(book.getCategory() != null ? book.getCategory().getId() : null)
                .publicationDate(book.getPublicationDate())
                .edition(book.getEdition())
                .language(book.getLanguage())
                .format(book.getFormat())
                .pages(book.getPages())
                .price(book.getPrice())
                .stock(book.getStock())
                .images(book.getImages() != null
                        ? book.getImages().stream()
                        .map(image -> ImageDto.builder()
                                .bookId(book.getId())
                                .imageUrl(image.getImageUrl())
                                .imageType(image.getImageType())
                                .imageOrder(image.getImageOrder())
                                .altText(image.getAltText())
                                .build())
                        .toList()
                        : new ArrayList<>())
                .build();
    }

    public Book asBook(Long bookId, WriteBookRequestDto bookDto) {
        Book oldBook = bookJpaRepository.findById(bookId).orElseThrow(
                () -> new BookNotFoundException("Book with ID " + bookId + " not found.")
        );
        oldBook.setIsbn(bookDto.getIsbn());
        oldBook.setTitle(bookDto.getTitle());
        oldBook.setDescription(bookDto.getDescription());
        oldBook.setShortDescription(bookDto.getShortDescription());
        oldBook.setPublicationDate(bookDto.getPublicationDate());
        oldBook.setEdition(bookDto.getEdition());
        oldBook.setLanguage(bookDto.getLanguage());
        oldBook.setFormat(bookDto.getFormat());
        oldBook.setPages(bookDto.getPages());
        oldBook.setPrice(bookDto.getPrice() != null ? bookDto.getPrice() : BigDecimal.ZERO);
        oldBook.setStock(bookDto.getStock() != null ? bookDto.getStock() : 0);
        oldBook.setAuthor(getAuthorFromDto(bookDto.getAuthorId()));
        oldBook.setPublisher(getPublisherFromDto(bookDto.getPublisherId()));
        oldBook.setCategory(getCategoryFromDto(bookDto.getCategoryId()));
        oldBook.setUpdatedAt(java.time.LocalDateTime.now());

        oldBook.getImages().clear();
        bookJpaRepository.flush();
        oldBook.getImages().addAll(getImagesFromDto(oldBook, bookDto.getImages()));
        return oldBook;
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

    private List<Image> getImagesFromDto(Book book, List<ImageDto> imagesDto) {
        List<Image> images = new ArrayList<>();
        if (imagesDto == null) {
            return images;
        }
        imagesDto.forEach(image -> {
            Image imageModified = Image.builder()
                    .book(book)
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
