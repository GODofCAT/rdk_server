package com.example.rdk.repositories;

import com.example.rdk.models.WorkType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkTypeRepository extends JpaRepository<WorkType, Integer> {
    WorkType findAllById(int id);

    List<WorkType> findAllByOrderByIdAsc();

    WorkType findByMobileName(String mobileName);
}
