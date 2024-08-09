package com.example.iruka_backend.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.iruka_backend.entity.Word;
import com.example.iruka_backend.service.WordService;

@RestController
@RequestMapping("/api/word/{deckId}")
@CrossOrigin(origins = "http://localhost:3000")
public class WordController {
	
	@Autowired
	private WordService wordService;
	
	@GetMapping
	public List<Word> getWordsByDeckId(@PathVariable("deckId") Long deckId) {
		return wordService.getWordsByDeckId(deckId);
	}
	
	@PostMapping
	public Word createWord(@PathVariable("deckId") Long deckId, @RequestBody Word word) {
		word.setDeckId(deckId);
		word.setMasteryStatusId(1L);
		word.setLastPracticedDate(LocalDateTime.now());
		word.setNextPracticeDate(LocalDateTime.now());
		word.setCorrect_count(0L);
		word.setIncorrect_count(0L);
		word.setCreatedAt(LocalDateTime.now());
		word.setUpdatedAt(LocalDateTime.now());
		return wordService.save(word);
	}
}