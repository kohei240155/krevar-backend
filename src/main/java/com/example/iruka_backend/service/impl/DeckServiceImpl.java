package com.example.iruka_backend.service.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.iruka_backend.entity.DeckEntity;
import com.example.iruka_backend.repository.DeckRepository;
import com.example.iruka_backend.service.DeckService;
import com.example.iruka_backend.service.QuizService;

@Service
public class DeckServiceImpl implements DeckService {

  @Autowired
  private DeckRepository deckRepository;

  @Autowired
  private QuizService quizService; // Added: dependency on QuizService

  @Override
  public List<DeckEntity> getAllDecks() {
    return deckRepository.findByDeletedAtIsNull(); // ソフトデリートされたデッキを除外
  }

  @Override
  public DeckEntity save(DeckEntity deck) {
    return deckRepository.save(deck);
  }

  @Override
  public DeckEntity updateDeckName(Long id, String newDeckName) {
    DeckEntity deck =
        deckRepository.findById(id).orElseThrow(() -> new RuntimeException("Deck not found"));
    deck.setDeckName(newDeckName);
    return deckRepository.update(deck);
  }

  @Override
  public void deleteDeck(Long id) {
    DeckEntity deck =
        deckRepository.findById(id).orElseThrow(() -> new RuntimeException("Deck not found"));
    deck.setDeletedAt(Timestamp.valueOf(LocalDateTime.now())); // 削除フラグを設定
    deckRepository.save(deck);
  }

  // ページネーション対応のメソッドを実装
  @Override
  public Page<DeckEntity> getDecks(Pageable pageable) {
    return deckRepository.findAllByDeletedAtIsNull(pageable); // ソフトデリートされたデッキを除外
  }

  @Override
  public void softDeleteDeck(Long id) {
    DeckEntity deck =
        deckRepository.findById(id).orElseThrow(() -> new RuntimeException("Deck not found"));
    deck.setDeletedAt(Timestamp.valueOf(LocalDateTime.now())); // 削除フラグを設定
    deckRepository.save(deck);
  }

  @Override
  public long countActiveDecks() {
    return deckRepository.countByDeletedAtIsNull(); // 追加: 有効なデッキのカウント
  }

  @Override
  public void setQuizService(QuizService quizService) {
    this.quizService = quizService;
  }

  @Override
  public List<DeckEntity> getAllDecksSortedByCorrectQuestions() {
    List<DeckEntity> allDecks = deckRepository.findByDeletedAtIsNull(); // ソフトデリートされたデッキを除外
    return allDecks.stream().sorted((d1, d2) -> {
      long correctQuestions1 = quizService.getCorrectWordCountByDeckId(d1.getId());
      long correctQuestions2 = quizService.getCorrectWordCountByDeckId(d2.getId());
      return Long.compare(correctQuestions1, correctQuestions2);
    }).collect(Collectors.toList());
  }

  @Override
  public List<DeckEntity> getAllDecksSortedByDueTodayAndIncorrectWords() {
    List<DeckEntity> allDecks = deckRepository.findByDeletedAtIsNull();
    return allDecks.stream().sorted((d1, d2) -> {
      long incorrectWords1 = quizService.getIncorrectWordCountByDeckIdDueToday(d1.getId());
      long incorrectWords2 = quizService.getIncorrectWordCountByDeckIdDueToday(d2.getId());
      return Long.compare(incorrectWords2, incorrectWords1); // 降順にソート
    }).collect(Collectors.toList());
  }
}
