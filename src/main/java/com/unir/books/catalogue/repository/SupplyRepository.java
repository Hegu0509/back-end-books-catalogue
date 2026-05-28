package com.unir.supplies.orders.repository;

import com.unir.supplies.orders.repository.model.Supply;
import com.unir.supplies.orders.repository.predicate.SearchCriteria;
import com.unir.supplies.orders.repository.predicate.SearchFields;
import com.unir.supplies.orders.repository.predicate.SearchOperation;
import com.unir.supplies.orders.repository.predicate.SearchStatement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SupplyRepository {

    private final SupplyJpaRepository supplyJpaRepository;

    public List<Supply> getSupplies(String name, String description, String fullDescription, String type, Double price, Integer stock) {

        SearchCriteria<Supply> spec = new SearchCriteria<>();

        if (StringUtils.hasText(name)) {
            spec.add(new SearchStatement(SearchFields.NAME, name, SearchOperation.MATCH));
        }

        if (StringUtils.hasText(description)) {
            spec.add(new SearchStatement(SearchFields.DESCRIPTION, description, SearchOperation.MATCH));
        }

        if (StringUtils.hasText(fullDescription)) {
            spec.add(new SearchStatement(SearchFields.FULL_DESCRIPTION, fullDescription, SearchOperation.MATCH));
        }

        if (StringUtils.hasText(type)) {
            spec.add(new SearchStatement(SearchFields.TYPE, type, SearchOperation.EQUAL));
        }

        if (price != null && price > 0) {
            spec.add(new SearchStatement(SearchFields.PRICE, price, SearchOperation.LESS_THAN_EQUAL));
        }

        if (stock != null && stock > 0) {
            spec.add(new SearchStatement(SearchFields.STOCK, stock, SearchOperation.GREATER_THAN_EQUAL));
        }

        return supplyJpaRepository.findAll(spec);
    }

    public List<Supply> getSupplies() {
        return supplyJpaRepository.findAvailableSupplies();
    }

    public List<Supply> getSupplies(Integer size, Integer page) {
        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException("Page number must be non-negative and size must be positive.");
        }
        return supplyJpaRepository.findAll(Pageable.ofSize(size).withPage(page)).getContent();
    }

    public List<Supply> getSupplies(
            String name,
            String description,
            String fullDescription,
            String type,
            Double price,
            Integer stock,
            Integer pageSize,
            Integer page) {

        SearchCriteria<Supply> spec = new SearchCriteria<>();

        if (StringUtils.hasText(name)) {
            spec.add(new SearchStatement(SearchFields.NAME, name, SearchOperation.MATCH));
        }

        if (StringUtils.hasText(description)) {
            spec.add(new SearchStatement(SearchFields.DESCRIPTION, description, SearchOperation.MATCH));
        }

        if (StringUtils.hasText(fullDescription)) {
            spec.add(new SearchStatement(SearchFields.FULL_DESCRIPTION, fullDescription, SearchOperation.MATCH));
        }

        if (StringUtils.hasText(type)) {
            spec.add(new SearchStatement(SearchFields.TYPE, type, SearchOperation.EQUAL));
        }

        if (price != null && price > 0) {
            spec.add(new SearchStatement(SearchFields.PRICE, price, SearchOperation.LESS_THAN_EQUAL));
        }

        if (stock != null && stock > 0) {
            spec.add(new SearchStatement(SearchFields.STOCK, stock, SearchOperation.GREATER_THAN_EQUAL));
        }

        return supplyJpaRepository.findAll(spec, Pageable.ofSize(pageSize).withPage(page)).getContent();
    }
}
