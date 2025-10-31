package com.learnwithravi.stockservice.repository;


import com.learnwithravi.stockservice.dto.OrderStocks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface StockRepository extends JpaRepository<OrderStocks, String> {
}
