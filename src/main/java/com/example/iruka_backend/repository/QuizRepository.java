package com.example.iruka_backend.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.iruka_backend.entity.WordEntity;

@Repository
public interface QuizRepository extends JpaRepository<WordEntity, Long> {

	@Query("SELECT w FROM WordEntity w WHERE w.nextPracticeDate <= :date AND w.deckId = :deckId ORDER BY w.nextPracticeDate ASC")
	Optional<WordEntity> findFirstWordByDeckIdAndDate(
			@Param("date") LocalDateTime date,
			@Param("deckId") Long deckId
			);

	@Query("SELECT COUNT(w) FROM WordEntity w WHERE w.deckId = :deckId AND w.isCorrect = FALSE AND w.nextPracticeDate = CURRENT_DATE")
	Long findCountByDeckIdAndIsCorrectFalseAndNextPracticeDate(@Param("deckId") Long deckId);

	@Query("SELECT COUNT(w) FROM WordEntity w WHERE w.deckId = :deckId AND w.isCorrect = TRUE AND w.nextPracticeDate = CURRENT_DATE")
	Long findCountByDeckIdAndIsCorrectTrueAndNextPracticeDate(@Param("deckId") Long deckId);

	@Query("SELECT COUNT(w) FROM WordEntity w WHERE w.deckId = :deckId AND w.nextPracticeDate = CURRENT_DATE")
	Long findTodayQuestionCountByDeckId(@Param("deckId") Long deckId);
}