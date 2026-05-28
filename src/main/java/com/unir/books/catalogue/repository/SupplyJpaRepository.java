package com.unir.supplies.orders.repository;

import com.unir.supplies.orders.repository.model.Supply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplyJpaRepository extends
        JpaRepository<Supply, Integer>,
        JpaSpecificationExecutor<Supply>,
        PagingAndSortingRepository<Supply, Integer> {

    // Consulta con JPQL
    @Query("SELECT s FROM Supply s WHERE s.stock > 0")
    List<Supply> findAvailableSupplies();

    // Consulta nativa SQL equivalente a la anterior
    @Query(
            value = "SELECT * FROM supply WHERE stock > 0",
            nativeQuery = true)
    List<Supply> findAvailableSuppliesNative();

    // Consulta con JPQL
    @Query("SELECT s FROM Supply s " +
            "LEFT JOIN FETCH s.specifications " +
            "LEFT JOIN FETCH s.images")
    List<Supply> findAllWithDetails();

    // Consulta nativa SQL equivalente a la anterior
    @Query(
            value = "SELECT s.* FROM supply s " +
                    "LEFT JOIN spec ss ON s.id = ss.supply_id " +
                    "LEFT JOIN image si ON s.id = si.supply_id",
            nativeQuery = true)
    List<Supply> findAllWithDetailsNative();

    // Consulta por derivacion de nombre de metodo
    List<Supply> findByTypeIgnoreCase(String type);

    // Consulta por derivacion de nombre de metodo
    List<Supply> findByNameContainingIgnoreCase(String name);
}
