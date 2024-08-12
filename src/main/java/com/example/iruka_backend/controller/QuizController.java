package com.example.iruka_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.iruka_backend.entity.Word;
import com.example.iruka_backend.service.QuizService;

@RestController
@RequestMapping("/api/quiz/normal/{deckId}")
@CrossOrigin(origins = "http://localhost:3000")
public class QuizController {

	@Autowired
	private QuizService quizService;

	@GetMapping
	public List<Word> getQuestionsByDeckId(@PathVariable("deckId") Long deckId) {
		return quizService.getQuestionsByDeckId(deckId);
	}
}
