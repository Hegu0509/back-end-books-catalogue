package com.unir.books.catalogue.service;

import com.unir.books.catalogue.controller.model.GetBookResponseDto;
import com.unir.books.catalogue.controller.model.WriteBookRequestDto;
import com.unir.books.catalogue.exception.AuthorNotFoundException;
import com.unir.books.catalogue.exception.CategoryNotFoundException;
import com.unir.books.catalogue.exception.PublisherNotFoundException;
import com.unir.books.catalogue.repository.AuthorJpaRepository;
import com.unir.books.catalogue.repository.BookJpaRepository;
import com.unir.books.catalogue.repository.CategoryJpaRepository;
import com.unir.books.catalogue.repository.PublisherJpaRepository;
import com.unir.books.catalogue.repository.model.*;
import com.unir.books.catalogue.utils.BookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@RequiredArgsConstructor
@Service
public class CreateBooksService {

    private final BookJpaRepository bookJpaRepository;
    private final AuthorJpaRepository authorJpaRepository;
    private final PublisherJpaRepository publisherJpaRepository;
    private  final CategoryJpaRepository categoryJpaRepository;
    private final BookMapper bookMapper;

    @Transactional
    public GetBookResponseDto createBook(WriteBookRequestDto request) {
        // Crear la entidad Book principal
        Book book = Book.builder()
                .isbn(request.getIsbn())
                .title(request.getTitle())
                .description(request.getDescription())
                .shortDescription(request.getShortDescription())
                .publicationDate(request.getPublicationDate())
                .edition(request.getEdition())
                .language(request.getLanguage())
                .format(request.getFormat())
                .pages(request.getPages() != null ? request.getPages() : 0)
                .price(request.getPrice() != null ? request.getPrice() : BigDecimal.ZERO)
                .stock(request.getStock() != null ? request.getStock() : 0)
                .visible("S")
                .valoracion(new BigDecimal("5"))
                .createdAt(java.time.LocalDateTime.now())
                .build();

        // Author
        if (request.getAuthorId() != null) {
            Author author = authorJpaRepository.findById(request.getAuthorId())
                    .orElseThrow(() -> new AuthorNotFoundException("Author not found with id: " + request.getAuthorId()));
            book.setAuthor(author);
        } else {
            throw new AuthorNotFoundException("Author not found with id: " + request.getAuthorId());
        }

        if (request.getPublisherId() != null) {
            Publisher publisher = publisherJpaRepository.findById(request.getPublisherId())
                    .orElseThrow(() -> new PublisherNotFoundException("Publisher not found with id: " + request.getPublisherId()));
            book.setPublisher(publisher);
        } else {
            throw new PublisherNotFoundException("Publisher not found with id: " + request.getPublisherId());
        }

        if (request.getCategoryId() != null) {
            Category category = categoryJpaRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + request.getCategoryId()));
            book.setCategory(category);
        } else {
            throw new CategoryNotFoundException("Category not found with id: " + request.getCategoryId());
        }

        // Crear imágenes si existen
        if (request.getImages() != null && !request.getImages().isEmpty()) {
            List<Image> images = new ArrayList<>();
            request.getImages().forEach(image -> {
                Image imageCreate = Image.builder()
                        .book(book)
                        .imageUrl(image.getImageUrl() != null && !image.getImageUrl().isEmpty() ? image.getImageUrl() : "Sin imagen")
                        .imageType(image.getImageType() != null ? image.getImageType() : "cover")
                        .imageOrder(image.getImageOrder()!= null ? image.getImageOrder() : null)
                        .altText(image.getAltText() != null ? image.getAltText() : null)
                        .build();
                images.add(imageCreate);
            });
            book.setImages(images);
        }

        // Guardar la entidad
        Book savedBook = bookJpaRepository.save(book);
        return bookMapper.asGetBookResponseDto(savedBook);
    }
}
