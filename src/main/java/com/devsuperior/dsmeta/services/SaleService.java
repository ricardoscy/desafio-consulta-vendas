package com.devsuperior.dsmeta.services;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

@Service
public class SaleService {

    @Autowired
    private SaleRepository repository;

    public SaleMinDTO findById(Long id) {
        Optional<Sale> result = repository.findById(id);
        Sale entity = result.get();
        return new SaleMinDTO(entity);
    }

    public Page<SaleMinDTO> salesReport(String minDate, String maxDate, String name, Pageable pageable) {
        LocalDate lastDate = maxDate.isBlank() ? LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault())
                : LocalDate.parse(maxDate);

        LocalDate firstDate = minDate.isBlank() ? lastDate.minusYears(1L) : LocalDate.parse(minDate);

        Page<Sale> sales = repository.salesReport(firstDate, lastDate, name, pageable);
        return sales.map(SaleMinDTO::new);
    }
}
