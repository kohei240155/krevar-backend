package com.example.iruka_backend.service.impl;

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

  // @Autowired
  // private WordRepository wordRepository;

  // @Override
  // public List<WordEntity> getWordsByDeckId(Long deckId) {
  // return wordRepository.findWordsByDeckId(deckId); // 修正
  // }

  // @Override
  // public WordEntity save(WordEntity word) {
  // return wordRepository.save(word);
  // }

  // @Override
  // public Optional<WordEntity> getWordById(Long wordId) {
  // return wordRepository.findById(wordId);
  // }

  // @Override
  // public WordEntity update(WordEntity word) {
  // return wordRepository.update(word);
  // }

  // @Override
  // public Page<WordEntity> getWords(Pageable pageable) {
  // return wordRepository.findAllByDeletedAtIsNull(pageable);
  // }

  // @Override
  // public long countActiveWords() {
  // return wordRepository.countByDeletedAtIsNull();
  // }
}
