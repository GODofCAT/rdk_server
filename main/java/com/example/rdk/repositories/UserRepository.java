package com.example.rdk.repositories;

import com.example.rdk.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByLoginAndPassword(String login, String password);
}
