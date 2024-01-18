package com.kh.totalapp.controller;

import org.elasticsearch.action.search.SearchResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.kh.totalapp.service.StockService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/elastic/stock")
public class StockController {
    private final StockService stockService;

    @GetMapping("/search")
    public ResponseEntity<?> searchByStockCode(@RequestParam String stockCode) throws Exception {
        return ResponseEntity.ok(stockService.searchByStockCode(stockCode));
    }
    @GetMapping("/searchByName")
    public ResponseEntity<?> searchByCompanyName(@RequestParam String companyName) throws Exception {
        return ResponseEntity.ok(stockService.searchByCompanyName(companyName));
    }
}
