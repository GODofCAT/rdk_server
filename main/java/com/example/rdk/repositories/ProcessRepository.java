package com.example.rdk.repositories;

import com.example.rdk.models.Log;
import com.example.rdk.models.Process;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessRepository extends JpaRepository<Process, Integer> {
}
