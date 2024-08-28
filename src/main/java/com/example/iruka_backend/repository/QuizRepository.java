package com.example.iruka_backend.repository;

import java.util.List;
import java.util.Optional;

import com.example.iruka_backend.entity.WordEntity;

public interface QuizRepository {
	List<WordEntity> findRandomWordByDeckIdAndDate(Long deckId);
	Long findCountByDeckIdAndIsNormalModeCorrectFalseAndNextPracticeDate(Long deckId);
	Long findTodayNormalQuestionCountByDeckId(Long deckId);
	Long findTodayExtraQuestionCountByDeckId(Long deckId);
	List<WordEntity> findWordsByDeckId(Long deckId);
	List<WordEntity> findExtraWordByDeckId(Long deckId);
	Long findCountByDeckIdAndIsNormalModeCorrectTrueAndNextPracticeDate(Long deckId);
	Optional<WordEntity> findById(Long id);
	WordEntity save(WordEntity word);
	WordEntity update(WordEntity word);
}