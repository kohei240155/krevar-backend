package com.example.iruka_backend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.iruka_backend.entity.Word;
import com.example.iruka_backend.repository.QuizRepository;
import com.example.iruka_backend.service.QuizService;

@Service
public class QuizServiceImpl implements QuizService {

	@Autowired
	private QuizRepository quizRepository;
	
	@Override
	public List<Word> getQuestionsByDeckId(Long deckId) {
		return quizRepository.findByDeckId(deckId);
	}

}
