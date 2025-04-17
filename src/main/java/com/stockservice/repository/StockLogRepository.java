package com.stockservice.repository;

import com.stockservice.entity.StockLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockLogRepository extends JpaRepository<StockLogEntity,Long> {
}
