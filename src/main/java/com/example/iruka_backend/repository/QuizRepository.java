package com.example.iruka_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.iruka_backend.entity.WordEntity;

@Repository
public interface QuizRepository extends JpaRepository<WordEntity, Long> {

	@Query("SELECT w FROM WordEntity w WHERE w.nextPracticeDate = CURRENT_DATE AND w.deckId = :deckId AND w.isNormalModeCorrect = FALSE ORDER BY RAND()")
	List<WordEntity> findRandomWordByDeckIdAndDate(@Param("deckId") Long deckId);

	@Query("SELECT COUNT(w) FROM WordEntity w WHERE w.deckId = :deckId AND w.isNormalModeCorrect = FALSE AND w.nextPracticeDate = CURRENT_DATE")
	Long findCountByDeckIdAndIsNormalModeCorrectFalseAndNextPracticeDate(@Param("deckId") Long deckId);

	@Query("SELECT COUNT(w) FROM WordEntity w WHERE w.deckId = :deckId AND w.nextPracticeDate = CURRENT_DATE")
	Long findTodayQuestionCountByDeckId(@Param("deckId") Long deckId);

	List<WordEntity> findWordsByDeckId(Long deckId);

	@Query("SELECT w FROM WordEntity w WHERE w.deckId = :deckId AND w.nextPracticeDate >= CURRENT_DATE ORDER BY w.correctCount ASC, w.nextPracticeDate ASC")
	List<WordEntity> findExtraWordByDeckId(@Param("deckId") Long deckId);

	@Query("SELECT COUNT(w) FROM WordEntity w WHERE w.deckId = :deckId AND w.isNormalModeCorrect = true AND w.nextPracticeDate = CURRENT_DATE")
	Long findCountByDeckIdAndIsNormalModeCorrectTrueAndNextPracticeDate(@Param("deckId") Long deckId);
}