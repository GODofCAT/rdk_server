package com.example.rdk.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "table_drugs")
public class Drug {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @Column(name = "name")
    @Getter
    public String name;

    @Column(name = "ingredients")
    @Getter
    public  String ingredients;
}
