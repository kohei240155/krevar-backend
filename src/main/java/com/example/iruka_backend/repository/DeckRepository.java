package com.example.iruka_backend.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.iruka_backend.entity.DeckEntity;

public interface DeckRepository {
  Optional<DeckEntity> findById(Long id);

  List<DeckEntity> findByDeletedAtIsNull();

  Page<DeckEntity> findAllByDeletedAtIsNull(Pageable pageable);

  long countByDeletedAtIsNull();

  Long findCountByDeckIdAndIsNormalModeCorrectFalseAndNextPracticeDate(Long deckId);

  Long countByDeckIdAndIsNormalModeCorrectTrue(Long deckId);

  Long findTodayQuestionCountByDeckId(Long deckId);

  long countByDeckIdAndNextPracticeDateAndIsNormalModeCorrect(
      Long deckId,
      LocalDate nextPracticeDate,
      Boolean isNormalModeCorrect);

  DeckEntity save(DeckEntity deck);

  DeckEntity update(DeckEntity deck);
}
