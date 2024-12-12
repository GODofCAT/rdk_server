package com.example.rdk.repositories;

import com.example.rdk.models.Company;
import com.example.rdk.models.Facility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacilityRepository extends JpaRepository<Facility, Integer> {
    List<Facility> findAllByCompanyId(int company_id);
    Facility findByUuid(String uuid);
    Facility findByName(String name);
    List<Facility> findAllByCompanyIdOrderByIdAsc(int company_id);
}
