package com.example.iruka_backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import com.example.iruka_backend.entity.UserEntity;
import com.example.iruka_backend.entity.WordEntity;
import com.example.iruka_backend.repository.QuizRepository;
import com.example.iruka_backend.repository.UserRepository;
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

  @Override
  public WordResponse getNormalQuiz(Long deckId) {

    // クイズを取得
    WordEntity word = quizRepository.findNormalQuizByDeckId(deckId);

    // 単語をWordResponseに変換
    WordResponse response = new WordResponse();
    response.setId(word.getId());
    response.setOriginalText(word.getOriginalText());
    response.setTranslatedText(word.getTranslatedText());
    response.setNuanceText(word.getNuanceText());
    response.setImageUrl(word.getImageUrl());

    return response;
  }


}
