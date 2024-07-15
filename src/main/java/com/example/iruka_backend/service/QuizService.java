package com.example.iruka_backend.service;

import java.util.List;

import com.example.iruka_backend.entity.Word;

public interface QuizService {
	List<Word> getQuestionsByDeckId(Long deckId);
}
