package com.example.krevar_backend.repository;

import java.util.List;
import com.example.krevar_backend.entity.WordEntity;
import com.example.krevar_backend.entity.WordUpdateEntity;
import com.example.krevar_backend.entity.WordCreateEntity;

public interface WordRepository {

  /**
   * デッキIDに紐づく単語を取得する
   *
   * @param deckId デッキID
   * @return 単語リスト
   */
  List<WordEntity> findByDeckId(Long deckId);

  /**
   * 単語を登録する
   *
   * @param wordCreateRequest 単語登録リクエスト
   */
  void save(WordCreateEntity wordCreateEntity);

  /**
   * 単語を更新する
   *
   * @param wordUpdateRequest 単語更新リクエスト
   */
  void update(WordUpdateEntity wordUpdateEntity);

  /**
   * 単語を取得する
   *
   * @param wordId 単語ID
   * @return 単語
   */
  WordEntity findById(Long wordId);

  /**
   * 単語を削除する
   *
   * @param wordId 単語ID
   */
  void delete(Long wordId);
}
