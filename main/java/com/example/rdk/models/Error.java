package com.example.rdk.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "table_errors")
public class Error {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "error")
    private String error;

    @Column(name = "date")
    private Date date;

    public Error(String error, Date date) {
        this.error = error;
        this.date = date;
    }

    public Error() {

    }


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
