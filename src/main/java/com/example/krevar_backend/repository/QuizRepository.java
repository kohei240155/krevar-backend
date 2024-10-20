package com.example.krevar_backend.repository;

import com.example.krevar_backend.entity.ExtraQuizResultEntity;
import com.example.krevar_backend.entity.NormalQuizResultEntity;
import com.example.krevar_backend.entity.WordEntity;

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
  void updateNormalQuiz(NormalQuizResultEntity quizResult);

  /**
   * エクストラクイズ取得
   *
   * @param deckId デッキID
   * @return クイズ
   */
  WordEntity findExtraQuizByDeckId(Long deckId);

  /**
   * エクストラクイズ更新
   *
   * @param wordId
   * @param isCorrect
   */
  void updateExtraQuiz(ExtraQuizResultEntity quizResult);

  /**
   * エクストラクイズの残数取得
   *
   * @param deckId デッキID
   * @return クイズの残数
   */
  int getLeftExtraQuizCount(Long deckId);

  /**
   * エクストラクイズリセット
   *
   * @param deckId
   */
  void resetExtraQuiz(Long deckId);

  /**
   * レビュー間隔を取得する
   *
   * @param wordId
   * @return レビュー間隔
   */
  int getReviewIntervalId(Long wordId);
}
