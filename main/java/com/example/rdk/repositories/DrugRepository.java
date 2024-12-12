package com.example.rdk.repositories;

import com.example.rdk.models.Company;
import com.example.rdk.models.Drug;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface DrugRepository  extends JpaRepository<Drug, Integer> {
    void deleteById(int id);
    List<Drug> findAllByOrderByIdAsc();
    Optional<Drug> findByName(String name);
}
