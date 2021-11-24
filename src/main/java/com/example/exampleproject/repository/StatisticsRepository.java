package com.example.exampleproject.repository;

import com.example.exampleproject.model.entity.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticsRepository extends JpaRepository<Statistic,Long> {
}
