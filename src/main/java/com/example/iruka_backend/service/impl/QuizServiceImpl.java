package com.example.iruka_backend.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.iruka_backend.entity.WordEntity;
import com.example.iruka_backend.repository.QuizRepository;
import com.example.iruka_backend.repository.ReviewIntervalRepository;
import com.example.iruka_backend.repository.WordRepository;
import com.example.iruka_backend.service.QuizService;

@Service
public class QuizServiceImpl implements QuizService {

	@Autowired
	private QuizRepository quizRepository;

	@Autowired
	private ReviewIntervalRepository reviewIntervalRepository;

	@Autowired
	private WordRepository wordRepository;

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

			if (isCorrect) {
				word.setCorrectCount(word.getCorrectCount() + 1);
				if (word.getReviewIntervalId() < 9) {
					word.setReviewIntervalId(word.getReviewIntervalId() + 1);
				}
				int intervalDays = reviewIntervalRepository.findById(word.getReviewIntervalId())
						.map(interval -> interval.getIntervalDays())
						.orElse(1);
				word.setNextPracticeDate(LocalDate.now().plusDays(intervalDays).atStartOfDay());
				word.setIsCorrect(false); // isCorrectをFalseに戻す
			} else {
				word.setIncorrectCount(word.getIncorrectCount() + 1);
				word.setReviewIntervalId(1L);
				word.setIsCorrect(false); // isCorrectをFalseに戻す
			}

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

	@Override
	public long getIncorrectWordCountByDeckIdDueToday(Long deckId) {
		return wordRepository.countByDeckIdAndNextPracticeDateAndIsCorrect(deckId, LocalDate.now().atStartOfDay(), false);
	}

	@Override
	public Optional<WordEntity> getExtraQuestionByDeckId(Long deckId) {
		List<WordEntity> words = quizRepository.findExtraWordByDeckId(deckId);
		return words.isEmpty() ? Optional.empty() : Optional.of(words.get(0));
	}

}