package com.gardenmanager.database_service.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO users (id, username, password) VALUES (:id, :username, :password)", nativeQuery = true)
    void InsertUser(@Param("id") int id, @Param("username") String username, @Param("password") String password);
}
