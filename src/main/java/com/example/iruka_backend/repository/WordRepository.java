package com.example.iruka_backend.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.iruka_backend.entity.WordEntity;

public interface WordRepository {
  List<WordEntity> findWordsByDeckId(Long deckId);

  WordEntity save(WordEntity word);

  Optional<WordEntity> findById(Long wordId);

  WordEntity update(WordEntity word);

  long countByDeletedAtIsNull();

  Long findCountByDeckIdAndIsNormalModeCorrectFalseAndNextPracticeDate(Long deckId);

  Long countByDeckIdAndIsNormalModeCorrectTrue(Long deckId);

  Long findTodayQuestionCountByDeckId(Long deckId);

  long countByDeckIdAndNextPracticeDateAndIsNormalModeCorrect(Long deckId,
      LocalDate nextPracticeDate, Boolean isNormalModeCorrect);

  Page<WordEntity> findAllByDeletedAtIsNull(Pageable pageable);
}
