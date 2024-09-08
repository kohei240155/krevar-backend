package com.example.iruka_backend.repository;

import com.example.iruka_backend.entity.QuizResultEntity;
import com.example.iruka_backend.entity.WordEntity;

public interface QuizRepository {

  /**
   * ノーマルクイズ取得
   *
   * @param deckId デッキID
   * @return クイズ
   */
  WordEntity findNormalQuizByDeckId(Long deckId);

  /**
   * ノーマルクイズの残数取得
   *
   * @param deckId デッキID
   * @return クイズの残数
   */
  int getLeftNormalQuizCount(Long deckId);

  /**
   * ノーマルクイズ更新
   *
   * @param wordId
   * @param isCorrect
   */
  void updateNormalQuiz(QuizResultEntity quizResult);

  /**
   * エクストラクイズ取得
   *
   * @param deckId デッキID
   * @return クイズ
   */
  WordEntity findExtraQuizByDeckId(Long deckId);

  /**
   * エクストラクイズの残数取得
   *
   * @param deckId デッキID
   * @return クイズの残数
   */
  int getLeftExtraQuizCount(Long deckId);

  /**
   * レビュー間隔を取得する
   *
   * @param wordId
   * @return レビュー間隔
   */
  int getReviewIntervalId(Long wordId);
}
