package com.example.iruka_backend.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import com.example.iruka_backend.entity.QuizResultEntity;
import com.example.iruka_backend.entity.UserEntity;
import com.example.iruka_backend.entity.WordEntity;
import com.example.iruka_backend.repository.QuizRepository;
import com.example.iruka_backend.repository.ReviewIntervalRepository;
import com.example.iruka_backend.repository.UserRepository;
import com.example.iruka_backend.requestdto.QuizResultUpdateRequest;
import com.example.iruka_backend.responsedto.QuizResponse;
import com.example.iruka_backend.service.QuizService;
import com.example.iruka_backend.util.SecurityUtil;

@Service
public class QuizServiceImpl implements QuizService {

  @Autowired
  private QuizRepository quizRepository;

  @Autowired
  private ReviewIntervalRepository reviewIntervalRepository;

  @Autowired
  private UserRepository userRepository;

  /**
   * ユーザーIDをチェックする
   *
   * @param requestedUserId リクエストされたユーザーID
   */
  @Override
  public void verifyUser(Long requestedUserId) {

    // ログインユーザーのメールアドレスを取得
    String username = SecurityUtil.getAuthenticatedUsername();

    // ログインユーザーを取得
    UserEntity user = userRepository.findByEmail(username);

    // ログインユーザーがリクエストされたユーザーと一致しない場合はエラーをスロー
    if (user == null || !user.getId().equals(requestedUserId)) {
      throw new AccessDeniedException("ユーザーにこのリソースへのアクセス権がありません");
    }
  }

  /**
   * デッキIDを指定してクイズを取得する
   *
   * @param deckId デッキID
   * @return クイズ
   */
  @Override
  public QuizResponse getNormalQuiz(Long deckId) {

    // クイズを取得
    WordEntity word = quizRepository.findNormalQuizByDeckId(deckId);

    // 単語をQuizResponseに変換
    QuizResponse response = new QuizResponse();
    response.setId(word.getId());
    response.setOriginalText(word.getOriginalText());
    response.setTranslatedText(word.getTranslatedText());
    response.setNuanceText(word.getNuanceText());
    response.setImageUrl(word.getImageUrl());
    response.setLeftQuizCount(quizRepository.getLeftNormalQuizCount(deckId));

    return response;
  }

  /**
   * デッキIDを指定してクイズの残数を取得する
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
  public void updateNormalQuiz(QuizResultUpdateRequest request) {

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

      // インクリメント用の不正解数を設定
      incorrectCount = 1;
    }

    // 更新日時を設定
    LocalDateTime updatedAt = LocalDateTime.now();

    // クイズ結果を生成
    QuizResultEntity quizResult = new QuizResultEntity(wordId, reviewIntervalId, nextPracticeDate,
        correctCount, incorrectCount, updatedAt);

    // クイズ結果を更新
    quizRepository.updateNormalQuiz(quizResult);

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
