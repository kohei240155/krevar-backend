package com.example.iruka_backend.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.iruka_backend.entity.WordEntity;
import com.example.iruka_backend.repository.WordRepository;
import com.example.iruka_backend.service.QuizService;

@Service
public class QuizServiceImpl implements QuizService {

	@Autowired
	private WordRepository wordRepository;

	@Override
	public Optional<WordEntity> getFirstQuestionByDeckId(Long deckId) {
		LocalDateTime now = LocalDateTime.now();
		List<WordEntity> words = wordRepository.findWordsByDeckId(deckId);
		if (words.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(words.get(0));
	}

	@Override
	public List<WordEntity> getQuestionsByDeckId(Long deckId) {
		return wordRepository.findWordsByDeckId(deckId);
	}

	@Override
	public void updateWordIsCorrect(Long wordId, Boolean isCorrect) {
		Optional<WordEntity> wordOpt = wordRepository.findById(wordId);
		if (wordOpt.isPresent()) {
			WordEntity word = wordOpt.get();
			word.setIsCorrect(isCorrect);
			wordRepository.save(word);
		}
	}

}