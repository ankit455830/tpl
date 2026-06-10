package com.tw.joi.delivery.controller;

import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> fetchStoreInventoryHealth(
        @RequestParam(name = "storeId") String storeId
    ) {
        return ResponseEntity.ok(Map.of("storeId", storeId, "healthy", true));
    }
}
