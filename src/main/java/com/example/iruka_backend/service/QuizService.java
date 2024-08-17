package com.example.iruka_backend.service;

import java.util.List;
import java.util.Optional;

import com.example.iruka_backend.entity.WordEntity;

public interface QuizService {
	Optional<WordEntity> getRandomQuestionByDeckId(Long deckId);
	List<WordEntity> getQuestionsByDeckId(Long deckId);
	void updateWordIsCorrect(Long wordId, Boolean isCorrect);
	Long getTodayQuestionCountByDeckId(Long deckId);
	Long getCorrectWordCountByDeckId(Long deckId);
	long getIncorrectWordCountByDeckIdDueToday(Long deckId); // Added: count of words with next_practice_date today and is_correct = 0
	Optional<WordEntity> getExtraQuestionByDeckId(Long deckId); // Added
}