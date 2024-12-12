package com.example.rdk.repositories;

import com.example.rdk.models.Error;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ErrorRepository extends JpaRepository<Error, Integer> {

}
