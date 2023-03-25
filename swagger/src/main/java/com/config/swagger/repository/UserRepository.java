package com.config.swagger.repository;

import com.config.swagger.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(nativeQuery = true,
    value = "SELECT * FROM users u WHERE u.full_name LIKE :name ")
    List<User> findByName(String name);
}
