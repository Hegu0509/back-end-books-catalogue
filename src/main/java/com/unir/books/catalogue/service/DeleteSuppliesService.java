package com.unir.supplies.orders.service;

import com.unir.supplies.orders.exception.SupplyNotFoundException;
import com.unir.supplies.orders.repository.SupplyJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class DeleteSuppliesService {

    private final SupplyJpaRepository supplyJpaRepository;

    @Transactional
    public void deleteSupply(int supplyId) {
        if (!supplyJpaRepository.existsById(supplyId)) {
            throw new SupplyNotFoundException("Supply with ID " + supplyId + " does not exist.");
        }
        supplyJpaRepository.deleteById(supplyId);
    }
}
