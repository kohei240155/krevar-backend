package com.example.iruka_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.iruka_backend.entity.WordEntity;

@Repository
public interface WordRepository extends JpaRepository<WordEntity, Long> {
	List<WordEntity> findByDeckId(Long deckId);
}
