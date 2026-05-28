package com.unir.supplies.orders.controller;

import com.unir.supplies.orders.controller.model.WriteSupplyRequestDto;
import com.unir.supplies.orders.controller.model.GetSuppliesResponseDto;
import com.unir.supplies.orders.controller.model.GetSupplyResponseDto;
import com.unir.supplies.orders.service.CreateSuppliesService;
import com.unir.supplies.orders.service.DeleteSuppliesService;
import com.unir.supplies.orders.service.GetSuppliesService;
import com.unir.supplies.orders.service.ModifySuppliesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class SuppliesController {

    private final GetSuppliesService getSuppliesService;
    private final ModifySuppliesService modifySuppliesService;
    private final DeleteSuppliesService deleteSuppliesService;
    private final CreateSuppliesService createSuppliesService;

    @GetMapping("supplies")
    public ResponseEntity<GetSuppliesResponseDto> getSupplies() {
        return ResponseEntity.ok(getSuppliesService.getSupplies());
    }

    @GetMapping("supplies/{supplyId}")
    public ResponseEntity<GetSupplyResponseDto> getSuppl(@PathVariable Long supplyId) {
        return ResponseEntity.ok(getSuppliesService.getSupply(supplyId.intValue()));
    }

    @PostMapping("supplies")
    public ResponseEntity<GetSupplyResponseDto> createSupply(@RequestBody WriteSupplyRequestDto request) {
        return ResponseEntity.ok(createSuppliesService.createSupply(request));
    }

    @PutMapping("supplies/{supplyId}")
    public ResponseEntity<GetSupplyResponseDto> updateSupply(
            @PathVariable Long supplyId,
            @RequestBody WriteSupplyRequestDto request) {
        return ResponseEntity.ok(modifySuppliesService.modifySupply(supplyId.intValue(), request));
    }

    @PatchMapping("supplies/{supplyId}")
    public ResponseEntity<GetSupplyResponseDto> updateSupply(
            @PathVariable Long supplyId,
            @RequestBody String jsonPart) {
        return ResponseEntity.ok(modifySuppliesService.modifySupply(supplyId.intValue(), jsonPart));
    }

    @DeleteMapping("supplies/{supplyId}")
    public ResponseEntity<Void> deleteSupply(@PathVariable Long supplyId) {
        deleteSuppliesService.deleteSupply(supplyId.intValue());
        return ResponseEntity.noContent().build();
    }

}
