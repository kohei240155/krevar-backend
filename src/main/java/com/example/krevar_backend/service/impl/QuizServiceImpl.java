package com.example.krevar_backend.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.krevar_backend.entity.ExtraQuizResultEntity;
import com.example.krevar_backend.entity.NormalQuizResultEntity;
import com.example.krevar_backend.entity.WordEntity;
import com.example.krevar_backend.repository.DeckRepository;
import com.example.krevar_backend.repository.QuizRepository;
import com.example.krevar_backend.repository.ReviewIntervalRepository;
import com.example.krevar_backend.requestdto.ExtraQuizResultUpdateRequest;
import com.example.krevar_backend.requestdto.NormalQuizResultUpdateRequest;
import com.example.krevar_backend.responsedto.QuizInfo;
import com.example.krevar_backend.responsedto.QuizResponse;
import com.example.krevar_backend.service.QuizService;

@Service
public class QuizServiceImpl implements QuizService {

  @Autowired
  private QuizRepository quizRepository;

  @Autowired
  private DeckRepository deckRepository;

  @Autowired
  private ReviewIntervalRepository reviewIntervalRepository;

  /**
   * ノーマルクイズ取得
   *
   * @param deckId デッキID
   * @return クイズ
   */
  @Override
  public QuizResponse getNormalQuiz(Long deckId) {

    // レスポンスを生成
    QuizResponse response = new QuizResponse();

    // クイズの残数を取得
    int leftQuizCount = getLeftNormalQuizCount(deckId);

    // デッキ名を取得
    String deckName = deckRepository.findById(deckId).getDeckName();
    response.setDeckName(deckName);

    // クイズの残数が0の場合はエラーをスロー
    if (leftQuizCount == 0) {
      response.setLeftQuizCount(0);
      return response;
    }

    // クイズを取得
    WordEntity word = quizRepository.findNormalQuizByDeckId(deckId);

    // デッキ名を取得

    // クイズ情報を生成
    QuizInfo quizData = new QuizInfo();
    quizData.setId(word.getId());
    quizData.setOriginalText(word.getOriginalText());
    quizData.setTranslatedText(word.getTranslatedText());
    quizData.setNuanceText(word.getNuanceText());
    quizData.setImageUrl(word.getImageUrl());
    quizData.setLeftQuizCount(leftQuizCount);

    // レスポンスに設定
    response.setQuizData(quizData);
    response.setLeftQuizCount(leftQuizCount);


    return response;
  }

  /**
   * ノーマルクイズの残数を取得
   *
   * @param deckId デッキID
   * @return クイズの残数
   */
  @Override
  public int getLeftNormalQuizCount(Long deckId) {
    return quizRepository.getLeftNormalQuizCount(deckId);
  }

  /**
   * ノーマルクイズ更新
   *
   * @param request
   */
  @Override
  public void updateNormalQuiz(NormalQuizResultUpdateRequest request) {

    Long wordId = request.getWordId();
    Boolean isCorrect = request.getIsCorrect();
    LocalDate nextPracticeDate = LocalDate.now();

    int correctCount = 0;
    int incorrectCount = 0;

    // レビュー間隔IDを取得
    int reviewIntervalId = quizRepository.getReviewIntervalId(wordId);

    if (isCorrect) {

      // レビュー間隔をインクリメント
      reviewIntervalId = incrementReviewIntervalId(reviewIntervalId);

      // 次の練習日を設定
      int intervalDay = reviewIntervalRepository.getIntervalDay(reviewIntervalId);
      nextPracticeDate = nextPracticeDate.plusDays(intervalDay);

      // インクリメント用の正解数を設定
      correctCount = 1;

    } else {

      // レビュー間隔をリセット
      reviewIntervalId = 1;

      // インクリメント用の不正解数を設定
      incorrectCount = 1;
    }

    // 更新日時を設定
    LocalDateTime updatedAt = LocalDateTime.now();

    // クイズ結果を生成
    NormalQuizResultEntity quizResult = new NormalQuizResultEntity(wordId, reviewIntervalId,
        nextPracticeDate, correctCount, incorrectCount, updatedAt);

    // クイズ結果を更新
    quizRepository.updateNormalQuiz(quizResult);
  }

  /**
   * エクストラクイズ取得
   *
   * @param deckId デッキID
   * @return クイズ
   */
  @Override
  public QuizResponse getExtraQuiz(Long deckId) {

    // レスポンスを生成
    QuizResponse response = new QuizResponse();

    // デッキ名を取得
    String deckName = deckRepository.findById(deckId).getDeckName();
    response.setDeckName(deckName);

    // クイズの残数を取得
    int leftQuizCount = getLeftExtraQuizCount(deckId);

    // クイズの残数が0の場合はエラーをスロー
    if (leftQuizCount == 0) {
      response.setLeftQuizCount(0);
      return response;
    }

    // クイズを取得
    WordEntity word = quizRepository.findExtraQuizByDeckId(deckId);


    // クイズ情報を生成
    QuizInfo quizData = new QuizInfo();
    quizData.setId(word.getId());
    quizData.setOriginalText(word.getOriginalText());
    quizData.setTranslatedText(word.getTranslatedText());
    quizData.setNuanceText(word.getNuanceText());
    quizData.setImageUrl(word.getImageUrl());
    quizData.setLeftQuizCount(leftQuizCount);

    // レスポンスに設定
    response.setQuizData(quizData);
    response.setLeftQuizCount(leftQuizCount);

    return response;
  }

  /**
   * エクストラクイズの残数を取得
   *
   * @param deckId デッキID
   * @return クイズの残数
   */
  @Override
  public int getLeftExtraQuizCount(Long deckId) {
    return quizRepository.getLeftExtraQuizCount(deckId);
  }

  /**
   * エクストラクイズ更新
   *
   * @param request
   */
  @Override
  public void updateExtraQuiz(ExtraQuizResultUpdateRequest request) {

    Long wordId = request.getWordId();
    Boolean isCorrect = request.getIsCorrect();
    LocalDateTime updatedAt = LocalDateTime.now();

    ExtraQuizResultEntity quizResult =
        new ExtraQuizResultEntity(wordId, isCorrect ? 1 : 0, updatedAt);

    // クイズ結果を更新
    quizRepository.updateExtraQuiz(quizResult);

  }

  /**
   * エクストラクイズリセット
   *
   * @param deckId
   */
  @Override
  public void resetExtraQuiz(Long deckId) {

    // エクストラクイズの結果をリセット
    quizRepository.resetExtraQuiz(deckId);
  }

  /**
   * レビュー間隔IDをインクリメントする
   *
   * @param reviewIntervalId レビュー間隔ID
   * @return インクリメント後のレビュー間隔ID
   */
  private int incrementReviewIntervalId(int reviewIntervalId) {

    if (reviewIntervalId < 10) {
      reviewIntervalId++;
    }
    return reviewIntervalId;
  }
}
