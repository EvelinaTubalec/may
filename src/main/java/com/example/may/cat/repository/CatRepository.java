package com.example.may.cat.repository;

import com.example.may.cat.model.Cat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

/**
 * @author Evelina Tubalets
 */
public interface CatRepository extends JpaRepository<Cat, UUID> {

    @Query("select cat from Cat cat where cat.id in :catIds")
    List<Cat> findByIds(final List<UUID> catIds);
}
