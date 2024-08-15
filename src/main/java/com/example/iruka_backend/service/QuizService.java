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
}