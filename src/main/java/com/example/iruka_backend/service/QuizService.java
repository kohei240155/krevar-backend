package com.example.iruka_backend.service;

import java.util.List;

import com.example.iruka_backend.entity.WordEntity;

public interface QuizService {
	List<WordEntity> getQuestionsByDeckId(Long deckId);
}
