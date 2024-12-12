package com.example.rdk.models;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@NoArgsConstructor
@Entity
@Table(name = "table_company")
public class Company {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @Column(name = "name")
    @Getter
    public String name;

    @Column(name = "inn")
    public String inn;

    @Column(name = "note")
    public String note;

    @Column(name = "login")
    public String login;

    @Column(name = "password")
    public String password;

    @Column(name = "isactive")
    public int isActive;

}
