package com.example.iruka_backend.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import com.example.iruka_backend.entity.UserEntity;
import com.example.iruka_backend.entity.WordEntity;
import com.example.iruka_backend.repository.UserRepository;
import com.example.iruka_backend.repository.WordRepository;
import com.example.iruka_backend.requestdto.WordRegisterRequest;
import com.example.iruka_backend.responsedto.WordInfo;
import com.example.iruka_backend.responsedto.WordListResponse;
import com.example.iruka_backend.service.WordService;
import com.example.iruka_backend.util.SecurityUtil;

@Service
public class WordServiceImpl implements WordService {

  @Autowired
  private WordRepository wordRepository;

  @Autowired
  private UserRepository userRepository;

  private static final String LOCAL_IMAGE_DIR = "C:/Git/iruka-frontend/public/images/testImages/";

  @Override
  public WordListResponse getWordListByDeckId(Long deckId, Long page, Long size) {

    // デッキに紐づく単語を取得
    List<WordEntity> words = wordRepository.findByDeckId(deckId);

    // 単語リストをWordInfoに変換
    List<WordInfo> wordInfoList = new ArrayList<>();

    for (WordEntity word : words) {
      WordInfo wordInfo = new WordInfo();
      wordInfo.setId(word.getId());
      wordInfo.setOriginalText(word.getOriginalText());
      wordInfo.setTranslatedText(word.getTranslatedText());
      wordInfo.setNuanceText(word.getNuanceText());
      wordInfo.setImageUrl(word.getImageUrl());
      wordInfo.setDeckId(word.getDeckId());
      wordInfoList.add(wordInfo);
    }

    WordListResponse response = new WordListResponse(wordInfoList);

    // ページネーション
    Long offset = page * size;
    Long limit = size;
    List<WordInfo> paginatedWordInfo =
        wordInfoList.stream().skip(offset).limit(limit).collect(Collectors.toList());

    response.setWordInfo(paginatedWordInfo);

    return response;
  }

  @Override
  public WordEntity getWordById(Long wordId) {
    return null;
  }

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
   * 単語を登録する
   *
   * @param wordRegisterRequest 単語登録リクエスト
   */
  @Override
  public void save(WordRegisterRequest wordRegisterRequest) {

    // 画像を保存
    String imagePath = wordRegisterRequest.getImageUrl();

    try {
      String savedImagePath = saveImage(imagePath);
      wordRegisterRequest.setImageUrl(savedImagePath);
    } catch (IOException e) {
      throw new RuntimeException("画像の保存に失敗しました", e);
    }
    // 単語を保存
    wordRepository.save(wordRegisterRequest);
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
}
