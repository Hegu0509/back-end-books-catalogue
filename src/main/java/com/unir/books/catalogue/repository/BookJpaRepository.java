package com.unir.books.catalogue.repository;

import com.unir.books.catalogue.repository.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookJpaRepository extends
        JpaRepository<Book, Long>,
        JpaSpecificationExecutor<Book>,
        PagingAndSortingRepository<Book, Long> {

    // Consulta con JPQL
    @Query("SELECT b FROM Book b WHERE b.stock > 0 AND b.visible = 'S' ")
    List<Book> findAvailableBooks();

    // Consulta nativa SQL equivalente a la anterior
    @Query(
            value = "SELECT * FROM books WHERE stock > 0 AND visible = 'S' ",
            nativeQuery = true)
    List<Book> findAvailableBooksNative();

    // Consulta con JPQL
    @Query("SELECT b FROM Book b " +
            "LEFT JOIN FETCH b.publisher " +
            "LEFT JOIN FETCH b.images " +
            "LEFT JOIN FETCH b.category " +
            "LEFT JOIN FETCH b.author " +
            "WHERE b.stock > 0 AND b.visible = 'S' "
    )
    List<Book> findAllWithDetails();

    // Consulta nativa SQL equivalente a la anterior
    @Query(
            value = "SELECT b.* FROM books b " +
                    "LEFT JOIN authors au ON b.author_id = au.id " +
                    "LEFT JOIN categories ca ON b.category_id = ca.id " +
                    "LEFT JOIN publishers p ON b.publisher_id = p.id "  +
                    "LEFT JOIN images i ON b.id = i.book_id " +
            " WHERE b.stock > 0 AND b.visible = 'S' ",
            nativeQuery = true)
    List<Book> findAllWithDetailsNative();


    Optional<Book> findByIdAndVisible(Long id, String visible);

    // Consulta por derivacion de nombre de metodo
    List<Book> findByFormatIgnoreCase(String format);

    // Consulta por derivacion de nombre de metodo
    List<Book> findByTitleContainingIgnoreCase(String title);
}
