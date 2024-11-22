package com.example.krevar_backend.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.krevar_backend.entity.DeckEntity;
import com.example.krevar_backend.entity.WordCreateEntity;
import com.example.krevar_backend.entity.WordEntity;
import com.example.krevar_backend.entity.WordUpdateEntity;
import com.example.krevar_backend.repository.DeckRepository;
import com.example.krevar_backend.repository.WordRepository;
import com.example.krevar_backend.requestdto.WordCreateRequest;
import com.example.krevar_backend.requestdto.WordUpdateRequest;
import com.example.krevar_backend.responsedto.WordInfo;
import com.example.krevar_backend.responsedto.WordListResponse;
import com.example.krevar_backend.responsedto.WordResponse;
import com.example.krevar_backend.service.WordService;

@Service
public class WordServiceImpl implements WordService {

  @Autowired
  private WordRepository wordRepository;

  @Autowired
  private DeckRepository deckRepository;

  private static final String LOCAL_IMAGE_DIR = "C:/Git/krevar-frontend/public/images/testImages/";

  /**
   * デッキに紐づく単語を取得する
   *
   * @param deckId デッキID
   * @param page ページ
   * @param size サイズ
   * @return 単語リスト
   */
  @Override
  public WordListResponse getWordListByDeckId(Long deckId, Long page, Long size) {

    // デッキを取得
    DeckEntity deck = deckRepository.findById(deckId);
    String deckName = deck.getDeckName();

    // デッキに紐づく単語を取得
    List<WordEntity> words = wordRepository.findByDeckId(deckId);

    // 単語リストをWordInfoに変換
    List<WordInfo> wordInfoList = new ArrayList<>();

    for (WordEntity word : words) {
      WordInfo wordInfo = new WordInfo();
      wordInfo.setId(word.getId());
      wordInfo.setOriginalText(word.getOriginalText());
      wordInfo.setTranslatedText(word.getTranslatedText());
      wordInfoList.add(wordInfo);
    }

    // 総単語数を取得
    int totalWordCount = wordInfoList.size();

    WordListResponse response = new WordListResponse(wordInfoList, deckName, totalWordCount);

    // ページネーション
    Long offset = page * size;
    Long limit = size;
    List<WordInfo> paginatedWordInfo =
        wordInfoList.stream().skip(offset).limit(limit).collect(Collectors.toList());

    response.setWordInfo(paginatedWordInfo);

    return response;
  }

  /**
   * 単語を取得する
   *
   * @param wordId 単語ID
   * @return 単語
   */
  @Override
  public WordResponse getWordById(Long wordId) {

    // 単語を取得
    WordEntity word = wordRepository.findById(wordId);

    // 単語をWordResponseに変換
    WordResponse response = new WordResponse();
    response.setId(word.getId());
    response.setOriginalText(word.getOriginalText());
    response.setTranslatedText(word.getTranslatedText());
    response.setNuanceText(word.getNuanceText());
    response.setImageUrl(word.getImageUrl());

    return response;
  }

  /**
   * 単語を登録する
   *
   * @param wordRegisterRequest 単語登録リクエスト
   */
  @Override
  public void save(Long userId, WordCreateRequest wordCreateRequest) {

    String originalText = wordCreateRequest.getOriginalText();
    String translatedText = wordCreateRequest.getTranslatedText();
    String nuanceText = wordCreateRequest.getNuanceText();
    String imageUrl = wordCreateRequest.getImageUrl();
    Long deckId = wordCreateRequest.getDeckId();

    WordCreateEntity wordCreateEntity =
        new WordCreateEntity(userId, originalText, translatedText, nuanceText, imageUrl, deckId);

    // 画像を保存
    String imagePath = wordCreateEntity.getImageUrl();

    // 一時的に画像を保存しない
    // try {
    // String savedImagePath = saveImage(imagePath);
    // wordCreateEntity.setImageUrl(savedImagePath);
    // } catch (IOException e) {
    // throw new RuntimeException("画像の保存に失敗しました", e);
    // }
    // 単語を保存
    wordRepository.save(wordCreateEntity);
  }

  /**
   * 画像を保存する
   *
   * @param imagePath 画像パス
   * @return 保存された画像パス
   */
  @Override
  public String saveImage(String imagePath) throws IOException {
    String fileName = Paths.get(URI.create(imagePath).getPath()).getFileName().toString();

    // ファイル名の正規化
    fileName = fileName.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");

    // URLからファイルをダウンロードしてローカルに保存
    try (InputStream in = URI.create(imagePath).toURL().openStream()) {
      Files.copy(in, Paths.get(LOCAL_IMAGE_DIR + fileName));
    }

    return LOCAL_IMAGE_DIR + fileName;
  }

  /**
   * 単語を更新する
   *
   * @param wordUpdateRequest 単語更新リクエスト
   */
  @Override
  public void update(WordUpdateRequest wordUpdateRequest) {

    Long wordId = wordUpdateRequest.getWordId();
    Long userId = wordUpdateRequest.getUserId();
    String originalText = wordUpdateRequest.getOriginalText();
    String translatedText = wordUpdateRequest.getTranslatedText();
    String nuanceText = wordUpdateRequest.getNuanceText();
    Long deckId = wordUpdateRequest.getDeckId();
    LocalDateTime updatedAt = LocalDateTime.now();

    WordUpdateEntity wordUpdateEntity = new WordUpdateEntity(wordId, userId, originalText,
        translatedText, nuanceText, deckId, updatedAt);

    wordRepository.update(wordUpdateEntity);
  }

  /**
   * 単語を削除する
   *
   * @param wordId 単語ID
   */
  @Override
  public void delete(Long wordId) {
    // 単語を削除
    wordRepository.delete(wordId);
  }
}
