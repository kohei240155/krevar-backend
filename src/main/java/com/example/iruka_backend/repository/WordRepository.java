package com.example.iruka_backend.repository;

import java.util.List;
import com.example.iruka_backend.entity.WordEntity;

public interface WordRepository {

  /**
   * デッキIDに紐づくクイズの進捗を取得する
   *
   * @param deckId デッキID
   * @return クイズの進捗
   */
  int getProgressByDeckId(Long deckId);

  /**
   * デッキIDに紐づく単語を取得する
   *
   * @param deckId デッキID
   * @return 単語リスト
   */
  List<WordEntity> findByDeckId(Long deckId);
}
