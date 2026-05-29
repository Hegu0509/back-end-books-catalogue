package com.unir.books.catalogue.repository;

import com.unir.books.catalogue.repository.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryJpaRepository extends
        JpaRepository<Category, Long>,
        JpaSpecificationExecutor<Category>,
        PagingAndSortingRepository<Category, Long> {

}
