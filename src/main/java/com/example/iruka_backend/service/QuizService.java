package com.example.iruka_backend.service;

import java.util.List;
import java.util.Optional;

import com.example.iruka_backend.entity.WordEntity;

public interface QuizService {
	Optional<WordEntity> getRandomQuestionByDeckId(Long deckId);
	List<WordEntity> getQuestionsByDeckId(Long deckId);
	void updateWordIsNormalModeCorrect(Long wordId, Boolean isNormalModeCorrect);
	Long getTodayNormalQuestionCountByDeckId(Long deckId); // Changed
	Long getTodayExtraQuestionCountByDeckId(Long deckId); // Added
	Long getCorrectWordCountByDeckId(Long deckId);
	long getIncorrectWordCountByDeckIdDueToday(Long deckId); // Added: count of words with next_practice_date today and is_normal_mode_correct = 0
	Optional<WordEntity> getExtraQuestionByDeckId(Long deckId); // Added
	void updateWordIsExtraModeCorrect(Long wordId, Boolean isExtraModeCorrect); // Added
	void resetExtraModeCorrectByDeckId(Long deckId); // Added
}