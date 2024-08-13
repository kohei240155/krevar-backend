package com.example.iruka_backend.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.iruka_backend.entity.WordEntity;

@Repository
public interface QuizRepository extends JpaRepository<WordEntity, Long> {

	@Query("SELECT w FROM Word w WHERE w.nextPracticeDate < :now AND w.deckId = :deckId")
	List<WordEntity> findTodaysWords(
			@Param("now") LocalDateTime now,
			@Param("deckId") Long deckId
			);
}
