package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SellerMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query(value = "SELECT sa FROM Sale sa JOIN FETCH sa.seller " +
            "WHERE sa.date BETWEEN :firstDate and :lastDate " +
            "and UPPER(sa.seller.name) LIKE UPPER(CONCAT('%', :name, '%'))",
            countQuery = "SELECT COUNT(sa) FROM Sale sa JOIN sa.seller")
    Page<Sale> salesReport(LocalDate firstDate, LocalDate lastDate, String name, Pageable pageable);

    @Query("SELECT new com.devsuperior.dsmeta.dto.SellerMinDTO(sa.seller.name, SUM(sa.amount)) " +
            "FROM Sale sa " +
            "WHERE sa.date BETWEEN :firstDate and :lastDate " +
            "GROUP BY sa.seller.name")
    List<SellerMinDTO> summarySalesSellers(LocalDate firstDate, LocalDate lastDate);
}
