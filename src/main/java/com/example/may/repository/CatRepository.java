package com.example.may.repository;

import com.example.may.model.Cat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Evelina Tubalets
 */
public interface CatRepository extends JpaRepository<Cat, Long> {

    @Query("select cat from Cat cat where cat.id=:catId")
    Optional<Cat> findById(final UUID catId);

    @Query("select cat from Cat cat where cat.id in :catIds")
    List<Cat> findByIds(final List<UUID> catIds);

    @Modifying
    @Transactional
    @Query("delete from Cat cat where cat.id=:catId")
    void deleteById(final UUID catId);
}
