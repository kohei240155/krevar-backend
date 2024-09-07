package com.example.iruka_backend.service;

import com.example.iruka_backend.entity.WordEntity;
import com.example.iruka_backend.responsedto.WordListResponse;

public interface WordService {

  /**
   * デッキに紐づく単語を取得する
   *
   * @param deckId デッキID
   * @return 単語
   */
  WordListResponse getWordListByDeckId(Long deckId, Long page, Long size);

  /**
   * 単語を取得する
   *
   * @param wordId 単語ID
   * @return 単語
   */
  WordEntity getWordById(Long wordId);

  /**
   * ユーザーIDをチェックする
   *
   * @param userId ユーザーID
   */
  void verifyUser(Long requestedUserId);
}
