package com.unir.books.catalogue.repository;

import com.unir.books.catalogue.repository.model.Author;
import com.unir.books.catalogue.repository.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorJpaRepository extends
        JpaRepository<Author, Long>,
        JpaSpecificationExecutor<Author>,
        PagingAndSortingRepository<Author, Long> {

}
