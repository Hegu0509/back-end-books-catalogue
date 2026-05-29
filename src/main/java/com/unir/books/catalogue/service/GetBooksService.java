package com.unir.books.catalogue.service;

import com.unir.books.catalogue.controller.model.GetBookResponseDto;
import com.unir.books.catalogue.controller.model.GetBooksResponseDto;
import com.unir.books.catalogue.exception.BookNotFoundException;
import com.unir.books.catalogue.repository.BookJpaRepository;
import com.unir.books.catalogue.repository.model.Book;
import com.unir.books.catalogue.utils.BookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetBooksService {

    private final BookJpaRepository repository;
    private final BookMapper mapper;

    @Transactional(readOnly = true)
    public GetBooksResponseDto getBooks() {
        List<Book> books = repository.findAvailableBooks();
        return GetBooksResponseDto.builder()
                .books(mapper.asBookDtoList(books))
                .build();
    }

    @Transactional(readOnly = true)
    public GetBookResponseDto getBook(Long bookId) {
        Optional<Book> book = repository.findByIdAndVisible(bookId, "S");
        return book.map(
                b -> GetBookResponseDto.builder()
                        .id(b.getId())
                        .title(b.getTitle())
                        .description(b.getDescription())
                        .isbn(b.getIsbn())
                        .valoracion(b.getValoracion())
                        .stock(b.getStock())
                        .price(b.getPrice())
                        .author(b.getAuthor() != null ? b.getAuthor().getName() : null)
                        .publisher(b.getPublisher() != null ? b.getPublisher().getName() : null)
                        .category(b.getCategory() != null ? b.getCategory().getName() : null)
                        .images(b.getImageUrls())
                        .build()
        ).orElseThrow(
                () -> new BookNotFoundException("Book not found with id: " + bookId));
    }
}
