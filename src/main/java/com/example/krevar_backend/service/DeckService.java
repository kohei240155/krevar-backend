package com.example.krevar_backend.service;

import com.example.krevar_backend.requestdto.DeckCreateRequest;
import com.example.krevar_backend.requestdto.DeckUpdateRequest;
import com.example.krevar_backend.responsedto.DeckInfo;
import com.example.krevar_backend.responsedto.DeckListResponse;

public interface DeckService {

  /**
   * ユーザーIDに紐づくデッキを取得する
   *
   * @param userId ユーザーID
   * @param page ページ番号
   * @param limit ページサイズ
   * @return デッキリスト
   */
  DeckListResponse getDeckList(Long userId, Long page, Long limit);

  /**
   * デッキIDからデッキを取得する
   *
   * @param deckId デッキID
   * @return デッキ
   */
  DeckInfo getDeck(Long deckId);

  /**
   * デッキを保存する
   *
   * @param userId ユーザーID
   * @param deckRequest デッキリクエスト
   * @return 保存されたデッキ
   */
  void save(Long userId, DeckCreateRequest deckCreateRequest);

  /**
   * デッキを更新する
   *
   * @param deckRequest デッキリクエスト
   */
  void update(Long userId, DeckUpdateRequest deckUpdateRequest, Long deckId);

  /**
   * デッキを削除する
   *
   * @param deckId デッキID
   */
  void delete(Long deckId);
}
