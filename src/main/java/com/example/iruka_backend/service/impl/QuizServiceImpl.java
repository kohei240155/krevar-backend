package com.example.iruka_backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import com.example.iruka_backend.entity.UserEntity;
import com.example.iruka_backend.entity.WordEntity;
import com.example.iruka_backend.repository.QuizRepository;
import com.example.iruka_backend.repository.UserRepository;
import com.example.iruka_backend.responsedto.QuizResponse;
import com.example.iruka_backend.responsedto.WordResponse;
import com.example.iruka_backend.service.QuizService;
import com.example.iruka_backend.util.SecurityUtil;

@Service
public class QuizServiceImpl implements QuizService {

  @Autowired
  private QuizRepository quizRepository;

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


}
