package com.example.iruka_backend.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.iruka_backend.entity.WordEntity;
import com.example.iruka_backend.service.WordService;

@RestController
@RequestMapping("/api/word")
@CrossOrigin(origins = "http://localhost:3000")
public class WordController {

	@Autowired
	private WordService wordService;

	@GetMapping("/deck/{deckId}")
	public List<WordEntity> getWordsByDeckId(@PathVariable("deckId") Long deckId) {
		return wordService.getWordsByDeckId(deckId);
	}

	@GetMapping("/{wordId}")
	public WordEntity getWordById(@PathVariable("wordId") Long wordId) {
		return wordService.getWordById(wordId).orElse(null);
	}

	@PostMapping("/{deckId}")
	public WordEntity createWord(@PathVariable("deckId") Long deckId, @RequestBody WordEntity word) {
		word.setDeckId(deckId);
		word.setReviewIntervalId(1L); // この行を追加
		word.setNextPracticeDate(LocalDateTime.now());
		word.setCorrectCount(0L); // メソッド名を修正
		word.setIncorrectCount(0L); // メソッド名を修正
		word.setCreatedAt(Timestamp.valueOf(LocalDateTime.now())); // 修正
		word.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now())); // 修正
		return wordService.save(word);
	}

	@PutMapping("/{wordId}")
	public WordEntity updateWord(@PathVariable("wordId") Long wordId, @RequestBody WordEntity word) {
		word.setId(wordId);
		word.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now())); // 修正
		return wordService.update(word);
	}
}