package com.unir.supplies.orders.controller;

import com.unir.supplies.orders.controller.model.GetSuppliesResponseDto;
import com.unir.supplies.orders.service.GetSuppliesWithPredicateAndPaginationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v3/")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class SuppliesControllerGetWithPredicateAndPagination {

    private final GetSuppliesWithPredicateAndPaginationService getSuppliesService;

    @GetMapping("supplies")
    public ResponseEntity<GetSuppliesResponseDto> getSupplies(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String fullDescription,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Double price,
            @RequestParam(required = false) Integer stock,
            @RequestParam(required = false, defaultValue = "5") Integer pageSize,
            @RequestParam(required = false, defaultValue = "0") Integer page
    ) {
        return ResponseEntity.ok(getSuppliesService.getSupplies(name, description, fullDescription, type, price, stock, pageSize, page));
    }
}
