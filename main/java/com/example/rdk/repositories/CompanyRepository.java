package com.example.rdk.repositories;

import com.example.rdk.models.Company;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
    Company findByLoginAndPassword(String login, String password);
    List<Company> findAllByOrderByIdAsc();
    Optional<Company> findByLogin(String login);
    Optional<Company> findByInn(String inn);
}
