package com.unir.books.catalogue.repository;

import com.unir.books.catalogue.repository.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageJpaRepository extends JpaRepository<Image, Integer> {

    List<Image> findByBookId(Integer bookId);

    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM images WHERE book_id = ?1")
    void deleteBySupplyId(Integer bookId);

}
