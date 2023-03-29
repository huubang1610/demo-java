package com.config.swagger.repository;

import com.config.swagger.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(nativeQuery = true,
            value = "SELECT * FROM users u WHERE u.full_name LIKE :name ")
    List<User> findByName(String name);

    @Query(nativeQuery = true,
            value = "SELECT * FROM users u WHERE u.user_name = :name ")
    Optional<User> findByUserName(String name);

    @Query(nativeQuery = true,
            value = "SELECT * FROM users u WHERE u.delete_at IS NULL ")
    Page<User> findAllToPage(Pageable pageable);
}
