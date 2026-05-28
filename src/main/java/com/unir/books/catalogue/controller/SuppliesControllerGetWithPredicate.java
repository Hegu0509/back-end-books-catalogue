package com.unir.supplies.orders.controller;

import com.unir.supplies.orders.controller.model.GetSuppliesResponseDto;
import com.unir.supplies.orders.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class SuppliesControllerGetWithPredicate {

    private final GetSuppliesWithPredicateService getSuppliesService;

    @GetMapping("supplies")
    public ResponseEntity<GetSuppliesResponseDto> getSupplies(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String fullDescription,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Double price,
            @RequestParam(required = false) Integer stock
    ) {
        return ResponseEntity.ok(getSuppliesService.getSupplies(name, description, fullDescription, type, price, stock));
    }
}
