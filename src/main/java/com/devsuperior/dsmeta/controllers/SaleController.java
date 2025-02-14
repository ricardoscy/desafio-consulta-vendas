package com.devsuperior.dsmeta.controllers;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.services.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

    @Autowired
    private SaleService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<SaleMinDTO> findById(@PathVariable Long id) {
        SaleMinDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping(value = "/report")
    public ResponseEntity<Page<SaleMinDTO>> getReport(@RequestParam(defaultValue = "") String minDate,
                                                      @RequestParam(defaultValue = "") String maxDate,
                                                      @RequestParam(defaultValue = "") String name,
                                                      Pageable pageable) {
        return ResponseEntity.ok(service.salesReport(minDate, maxDate, name, pageable));
    }

    @GetMapping(value = "/summary")
    public ResponseEntity<?> getSummary(@RequestParam(defaultValue = "") String minDate,
                                        @RequestParam(defaultValue = "") String maxDate) {
        return ResponseEntity.ok(service.summarySalesSellers(minDate, maxDate));
    }
}
