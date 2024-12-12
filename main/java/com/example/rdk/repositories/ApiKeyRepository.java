package com.example.rdk.repositories;

import com.example.rdk.models.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiKeyRepository extends JpaRepository<ApiKey, Integer> {
    ApiKey findByApiKey(String apiKey);
}
