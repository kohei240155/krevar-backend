package com.example.iruka_backend.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.iruka_backend.entity.WordEntity;
import com.example.iruka_backend.repository.QuizRepository;
import com.example.iruka_backend.service.QuizService;

@Service
public class QuizServiceImpl implements QuizService {

	@Autowired
	private QuizRepository quizRepository;

	@Override
	public Optional<WordEntity> getRandomQuestionByDeckId(Long deckId) {
		List<WordEntity> words = quizRepository.findRandomWordByDeckIdAndDate(deckId);
		return words.isEmpty() ? Optional.empty() : Optional.of(words.get(0));
	}

	@Override
	public List<WordEntity> getQuestionsByDeckId(Long deckId) {
		return quizRepository.findWordsByDeckId(deckId);
	}

	@Override
	public void updateWordIsCorrect(Long wordId, Boolean isCorrect) {
		Optional<WordEntity> wordOpt = quizRepository.findById(wordId);
		if (wordOpt.isPresent()) {
			WordEntity word = wordOpt.get();
			word.setIsCorrect(isCorrect);
			quizRepository.save(word);
		}
	}

	@Override
	public Long getTodayQuestionCountByDeckId(Long deckId) {
		return quizRepository.findTodayQuestionCountByDeckId(deckId);
	}

	@Override
	public Long getCorrectWordCountByDeckId(Long deckId) {
		return quizRepository.findCountByDeckIdAndIsCorrectTrueAndNextPracticeDate(deckId);
	}

}