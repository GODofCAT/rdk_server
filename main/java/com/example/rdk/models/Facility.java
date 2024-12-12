package com.example.rdk.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Entity
@Table(name = "table_facility")
public class Facility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @Column(name = "name")
    public String name;

    @Column(name = "address")
    public String address;

    @Column(name = "note")
    public String note;

    @Column(name = "uuid")
    public String uuid;

    @Column(name = "is_active")
    public int isActive;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id")
    public Company company;


    public Facility(String name, String address, String note, int isActive, Company company, String uuid) {
        this.name = name;
        this.address = address;
        this.note = note;
        this.isActive = isActive;
        this.company = company;
        this.uuid = uuid;
    }

    public Facility() {

    }
}
