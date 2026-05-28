package com.unir.books.catalogue.service;

import com.unir.books.catalogue.controller.model.GetBooksResponseDto;
import com.unir.books.catalogue.repository.BookRepository;
import com.unir.books.catalogue.repository.model.Book;
import com.unir.books.catalogue.utils.BookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetBooksWithPredicateService {

    private final BookRepository repository;
    private final BookMapper mapper;

    @Transactional(readOnly = true)
    public GetBooksResponseDto getBooks(
            String title,
            String isbn,
            String description,
            BigDecimal valoracion,
            Integer stock
    ) {

        List<Book> books;
        if (StringUtils.hasLength(title) || StringUtils.hasLength(isbn) || StringUtils.hasLength(description)
                || valoracion != null
                || stock != null) {
            books = repository.getBooks(title, isbn, description, valoracion, stock);
        } else {
            books = repository.getBooks();
        }
        return GetBooksResponseDto.builder()
                .books(mapper.asBookDtoList(books))
                .build();
    }
}
