package com.game.repository;

import com.game.entity.Round;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoundRepository extends JpaRepository<Round, Long> {
    List<Round> findAll();

    Optional<Round> findById(Long id);

    Page<Round> findAllByUserId(Long id, Pageable pageable);
}
