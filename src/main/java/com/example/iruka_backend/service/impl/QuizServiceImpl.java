package com.example.iruka_backend.service.impl;

import java.time.LocalDateTime;
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
//		LocalDateTime now = LocalDateTime.now();
//		TODO: 動作確認用
		LocalDateTime now = LocalDateTime.parse("2024-07-18T12:00:00");
		System.out.println("★：" + now.toString());
		System.out.println("★：" + deckId);
		return quizRepository.findTodaysWords(now, deckId);
	}

}
