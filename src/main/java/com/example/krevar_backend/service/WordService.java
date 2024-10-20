package com.example.krevar_backend.service;

import java.io.IOException;
import com.example.krevar_backend.requestdto.WordCreateRequest;
import com.example.krevar_backend.requestdto.WordUpdateRequest;
import com.example.krevar_backend.responsedto.WordListResponse;
import com.example.krevar_backend.responsedto.WordResponse;

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
  WordResponse getWordById(Long wordId);

  /**
   * 単語を登録する
   *
   * @param wordCreateRequest 単語登録リクエスト
   */
  void save(WordCreateRequest wordCreateRequest);

  /**
   * 画像を保存する
   *
   * @param imagePath 画像パス
   * @return 保存された画像パス
   */
  String saveImage(String imagePath) throws IOException;

  /**
   * 単語を更新する
   *
   * @param wordUpdateRequest 単語更新リクエスト
   */
  void update(WordUpdateRequest wordUpdateRequest);

  /**
   * 単語を削除する
   *
   * @param wordId 単語ID
   */
  void delete(Long wordId);
}
