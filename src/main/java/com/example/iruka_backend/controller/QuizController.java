package com.example.iruka_backend.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.iruka_backend.entity.WordEntity;
import com.example.iruka_backend.service.QuizService;

@RestController
@RequestMapping("/api/quiz")
@CrossOrigin(origins = "http://localhost:3000")
public class QuizController {

	@Autowired
	private QuizService quizService;

	@GetMapping("/normal/{deckId}")
	public Map<String, Object> getRandomQuestionByDeckId(@PathVariable("deckId") Long deckId) {
		Optional<WordEntity> randomQuestion = quizService.getRandomQuestionByDeckId(deckId);
		Long todayQuestionCount = quizService.getTodayQuestionCountByDeckId(deckId);

		Map<String, Object> response = new HashMap<>();
		response.put("randomQuestion", randomQuestion.orElse(null));
		response.put("todayQuestionCount", todayQuestionCount);

		return response;
	}

	@PostMapping("/answer/{wordId}")
	public void submitAnswer(@PathVariable("wordId") Long wordId, @RequestBody Map<String, Boolean> request) {
		Boolean isCorrect = request.get("isCorrect");
		quizService.updateWordIsCorrect(wordId, isCorrect);
	}
}