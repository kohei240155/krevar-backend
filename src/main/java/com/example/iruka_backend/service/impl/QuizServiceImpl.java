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

import java.sql.Timestamp; // Added import for Timestamp class

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
	public void updateWordIsNormalModeCorrect(Long wordId, Boolean isNormalModeCorrect) {
		Optional<WordEntity> wordOpt = quizRepository.findById(wordId);
		if (wordOpt.isPresent()) {
			WordEntity word = wordOpt.get();
			word.setIsNormalModeCorrect(isNormalModeCorrect);

			if (isNormalModeCorrect) {
				word.setCorrectCount(word.getCorrectCount() + 1);
				if (word.getReviewIntervalId() < 9) {
					word.setReviewIntervalId(word.getReviewIntervalId() + 1);
				}
				int intervalDays = reviewIntervalRepository.findById(word.getReviewIntervalId())
						.map(interval -> interval.getIntervalDays())
						.orElse(1);
				word.setNextPracticeDate(LocalDate.now().plusDays(intervalDays)); // LocalDateに変更
				word.setIsNormalModeCorrect(false); // isNormalModeCorrectをFalseに戻す
			} else {
				word.setIncorrectCount(word.getIncorrectCount() + 1);
				word.setReviewIntervalId(1L);
				word.setIsNormalModeCorrect(false); // isNormalModeCorrectをFalseに戻す
			}

			quizRepository.save(word);
		}
	}

	@Override
	public void updateWordIsExtraModeCorrect(Long wordId, Boolean isExtraModeCorrect) {
		Optional<WordEntity> wordOpt = quizRepository.findById(wordId);
		if (wordOpt.isPresent()) {
			WordEntity word = wordOpt.get();
			word.setIsExtraModeCorrect(isExtraModeCorrect);
			word.setUpdatedAt(Timestamp.valueOf(LocalDate.now().atStartOfDay())); // Set updated_at to current time

			quizRepository.save(word);
		}
	}

	@Override
	public void resetExtraModeCorrectByDeckId(Long deckId) {
		List<WordEntity> words = quizRepository.findWordsByDeckId(deckId);
		for (WordEntity word : words) {
			word.setIsExtraModeCorrect(false);
		}
		quizRepository.saveAll(words);
	}

	@Override
	public Long getTodayNormalQuestionCountByDeckId(Long deckId) {
		return quizRepository.findTodayNormalQuestionCountByDeckId(deckId); // Changed
	}

	@Override
	public Long getTodayExtraQuestionCountByDeckId(Long deckId) {
		return quizRepository.findTodayExtraQuestionCountByDeckId(deckId); // Added
	}

	@Override
	public Long getCorrectWordCountByDeckId(Long deckId) {
		return quizRepository.findCountByDeckIdAndIsNormalModeCorrectTrueAndNextPracticeDate(deckId);
	}

	@Override
	public long getIncorrectWordCountByDeckIdDueToday(Long deckId) {
		return wordRepository.countByDeckIdAndNextPracticeDateAndIsNormalModeCorrect(deckId, LocalDate.now(),false);
	}

	@Override
	public Optional<WordEntity> getExtraQuestionByDeckId(Long deckId) {
		List<WordEntity> words = quizRepository.findExtraWordByDeckId(deckId);
		return words.isEmpty() ? Optional.empty() : Optional.of(words.get(0));
	}

	@Override
	public Optional<WordEntity> getRandomExtraQuestionByDeckId(Long deckId) {
		List<WordEntity> words = quizRepository.findExtraWordByDeckId(deckId);
		if (words.isEmpty()) {
			return Optional.empty();
		}
		int randomIndex = (int) (Math.random() * words.size());
		return Optional.of(words.get(randomIndex));
	}

}