package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query(value = "SELECT sa FROM Sale sa JOIN FETCH sa.seller " +
            "WHERE sa.date BETWEEN :firstDate and :lastDate " +
            "and UPPER(sa.seller.name) LIKE UPPER(CONCAT('%', :name, '%'))",
            countQuery = "SELECT COUNT(sa) FROM Sale sa JOIN sa.seller")
    Page<Sale> salesReport(LocalDate firstDate, LocalDate lastDate, String name, Pageable pageable);
}
