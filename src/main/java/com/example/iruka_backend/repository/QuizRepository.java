package com.example.iruka_backend.repository;

import com.example.iruka_backend.entity.WordEntity;

public interface QuizRepository {

  /**
   * デッキIDを指定してクイズを取得する
   *
   * @param deckId デッキID
   * @return クイズ
   */
  WordEntity findNormalQuizByDeckId(Long deckId);
}
