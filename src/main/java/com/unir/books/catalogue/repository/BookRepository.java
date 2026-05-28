package com.unir.books.catalogue.repository;

import com.unir.books.catalogue.repository.model.Book;
import com.unir.books.catalogue.repository.predicate.SearchCriteria;
import com.unir.books.catalogue.repository.predicate.SearchFields;
import com.unir.books.catalogue.repository.predicate.SearchOperation;
import com.unir.books.catalogue.repository.predicate.SearchStatement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookRepository {

    private final BookJpaRepository bookJpaRepository;

    public List<Book> getBooks(String title, String isbn, String description, BigDecimal valoracion, Integer stock) {

        SearchCriteria<Book> spec = new SearchCriteria<>();

        if (StringUtils.hasText(title)) {
            spec.add(new SearchStatement(SearchFields.TITLE, title, SearchOperation.MATCH));
        }

        if (StringUtils.hasText(isbn)) {
            spec.add(new SearchStatement(SearchFields.ISBN, isbn, SearchOperation.MATCH));
        }

        if (StringUtils.hasText(description)) {
            spec.add(new SearchStatement(SearchFields.DESCRIPTION, description, SearchOperation.MATCH));
        }

        if (valoracion != null && new BigDecimal(String.valueOf(valoracion)).compareTo(BigDecimal.ZERO) > 0) {
            spec.add(new SearchStatement(SearchFields.VALORACION, valoracion, SearchOperation.LESS_THAN_EQUAL));
        }

        if (stock != null && stock > 0) {
            spec.add(new SearchStatement(SearchFields.STOCK, stock, SearchOperation.GREATER_THAN_EQUAL));
        }

        return bookJpaRepository.findAll(spec);
    }

    public List<Book> getBooks()  {
        return bookJpaRepository.findAvailableBooks();
    }

    public List<Book> getBooks(Integer size, Integer page)  {
        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException("Page number must be non-negative and size must be positive.");
        }
        return bookJpaRepository.findAll(Pageable.ofSize(size).withPage(page)).getContent();
    }

    public List<Book> getBooks(
            String title,
            String isbn,
            String description,
            BigDecimal valoracion,
            Integer stock,
            Integer pageSize,
            Integer page) {

        SearchCriteria<Book> spec = new SearchCriteria<>();

        if (StringUtils.hasText(title)) {
            spec.add(new SearchStatement(SearchFields.TITLE, title, SearchOperation.MATCH));
        }

        if (StringUtils.hasText(isbn)) {
            spec.add(new SearchStatement(SearchFields.ISBN, isbn, SearchOperation.MATCH));
        }

        if (StringUtils.hasText(description)) {
            spec.add(new SearchStatement(SearchFields.DESCRIPTION, description, SearchOperation.MATCH));
        }

        if (valoracion != null && new BigDecimal(String.valueOf(valoracion)).compareTo(BigDecimal.ZERO) > 0) {
            spec.add(new SearchStatement(SearchFields.VALORACION, valoracion, SearchOperation.LESS_THAN_EQUAL));
        }

        if (stock != null && stock > 0) {
            spec.add(new SearchStatement(SearchFields.STOCK, stock, SearchOperation.GREATER_THAN_EQUAL));
        }

        return bookJpaRepository.findAll(spec, Pageable.ofSize(pageSize).withPage(page)).getContent();
    }
}
