package com.nisemup.bakerymanager.repository;

import com.nisemup.bakerymanager.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;

@Repository
@Transactional
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);

    List<Category> findByNameContainingIgnoreCase(String keyword);

    @Modifying
    @Query("UPDATE Category c SET c.active = :active WHERE c.id = :id")
    void updateActiveStatus(Long id, boolean active);
}
