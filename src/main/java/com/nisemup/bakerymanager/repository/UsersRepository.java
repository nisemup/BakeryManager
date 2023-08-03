package com.nisemup.bakerymanager.repository;

import com.nisemup.bakerymanager.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);

    Optional<Users> findByUserId(Long id);

    @Modifying
    @Query("DELETE FROM Users u WHERE u.userId = ?1")
    void deleteById(Long id);
}
