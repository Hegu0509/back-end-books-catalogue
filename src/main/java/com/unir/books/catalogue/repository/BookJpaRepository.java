package com.unir.books.catalogue.repository;

import com.unir.books.catalogue.repository.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookJpaRepository extends
        JpaRepository<Book, Integer>,
        JpaSpecificationExecutor<Book>,
        PagingAndSortingRepository<Book, Integer> {

    // Consulta con JPQL
    @Query("SELECT b FROM Book b WHERE b.stock > 0")
    List<Book> findAvailableBooks();

    // Consulta nativa SQL equivalente a la anterior
    @Query(
            value = "SELECT * FROM books WHERE stock > 0",
            nativeQuery = true)
    List<Book> findAvailableBooksNative();

    // Consulta con JPQL
    @Query("SELECT b FROM Book b " +
            "LEFT JOIN FETCH b.publisher " +
            "LEFT JOIN FETCH b.images " +
            "LEFT JOIN FETCH b.category " +
            "LEFT JOIN FETCH b.author "
    )
    List<Book> findAllWithDetails();

    // Consulta nativa SQL equivalente a la anterior
    @Query(
            value = "SELECT b.* FROM books b " +
                    "LEFT JOIN authors au ON b.author_id = au.id " +
                    "LEFT JOIN categories ca ON b.category_id = ca.id " +
                    "LEFT JOIN publishers p ON b.publisher_id = p.id "  +
                    "LEFT JOIN images i ON b.id = i.book_id ",
            nativeQuery = true)
    List<Book> findAllWithDetailsNative();

    // Consulta por derivacion de nombre de metodo
    List<Book> findByFormatIgnoreCase(String format);

    // Consulta por derivacion de nombre de metodo
    List<Book> findByTitleContainingIgnoreCase(String title);
}
