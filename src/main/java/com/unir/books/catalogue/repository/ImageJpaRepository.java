package com.unir.books.catalogue.repository;

import com.unir.books.catalogue.repository.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageJpaRepository extends JpaRepository<Image, Long> {

    List<Image> findByBookId(Long bookId);

}
