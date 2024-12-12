package com.example.rdk.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@Entity
@Table(name = "table_api_keys")
public class ApiKey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @Column(name = "api_key")
    public String apiKey;

    @Column(name = "expiration_time")
    public String expirationTime;

    public ApiKey(String apiKey, String expirationTime) {
        this.apiKey = apiKey;
        this.expirationTime = expirationTime;
    }
}
