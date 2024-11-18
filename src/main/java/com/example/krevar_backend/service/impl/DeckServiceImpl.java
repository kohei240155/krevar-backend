package com.example.krevar_backend.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.krevar_backend.entity.DeckCreateEntity;
import com.example.krevar_backend.entity.DeckEntity;
import com.example.krevar_backend.entity.DeckUpdateEntity;
import com.example.krevar_backend.repository.DeckRepository;
import com.example.krevar_backend.requestdto.DeckCreateRequest;
import com.example.krevar_backend.requestdto.DeckUpdateRequest;
import com.example.krevar_backend.responsedto.DeckInfo;
import com.example.krevar_backend.responsedto.DeckListResponse;
import com.example.krevar_backend.service.DeckService;

@Service
public class DeckServiceImpl implements DeckService {

  @Autowired
  private DeckRepository deckRepository;

  /**
   * ユーザーIDに紐づくデッキ一覧を取得する
   *
   * @param userId ユーザーID
   * @param page ページ番号
   * @param size ページサイズ
   * @return デッキ一覧
   */
  @Override
  public DeckListResponse getDeckList(Long userId, Long page, Long size) {

    // デッキ一覧を取得
    List<DeckEntity> decks = deckRepository.findDecksByUserId(userId);

    // デッキ情報リストを作成
    List<DeckInfo> deckInfoList = new ArrayList<>();

    // デッキ一覧をデッキ情報に変換
    for (DeckEntity deck : decks) {
      DeckInfo deckInfo = new DeckInfo();
      deckInfo.setId(deck.getId());
      deckInfo.setDeckName(deck.getDeckName());
      int remainingQuestionCount = deckRepository.getRemainingQuestionCountByDeckId(deck.getId());
      deckInfo.setRemainingQuestionCount(remainingQuestionCount);
      deckInfoList.add(deckInfo);
    }

    // 残りの問題数の値が多い順に並び替え
    deckInfoList.sort(Comparator.comparingInt(DeckInfo::getRemainingQuestionCount).reversed());

    // ページネーション処理
    Long offset = page * size;
    Long limit = size;
    List<DeckInfo> paginatedDeckInfo =
        deckInfoList.stream().skip(offset).limit(limit).collect(Collectors.toList());

    // ページネーションしたデッキ情報を返却
    DeckListResponse response = new DeckListResponse(paginatedDeckInfo, deckInfoList.size());

    return response;
  }

  /**
   * デッキIDからデッキを取得する
   *
   * @param deckId デッキID
   * @return デッキ
   */
  @Override
  public DeckInfo getDeck(Long deckId) {

    DeckEntity deck = deckRepository.findById(deckId);

    DeckInfo deckInfo = new DeckInfo();
    deckInfo.setId(deck.getId());
    deckInfo.setDeckName(deck.getDeckName());
    deckInfo.setNativeLanguageId(deck.getNativeLanguageId());
    deckInfo.setLearningLanguageId(deck.getLearningLanguageId());

    return deckInfo;
  }

  /**
   * デッキを保存する
   *
   * @param deckCreateRequest デッキリクエスト
   */
  @Override
  public void save(Long userId, DeckCreateRequest deckCreateRequest) {

    // デッキエンティティを作成
    DeckCreateEntity deckCreateEntity =
        new DeckCreateEntity(userId, deckCreateRequest.getDeckName(),
            deckCreateRequest.getNativeLanguageId(), deckCreateRequest.getLearningLanguageId());

    // デッキを保存
    deckRepository.save(deckCreateEntity);
  }

  /**
   * デッキを更新する
   *
   * @param deckUpdateRequest デッキ更新リクエスト
   */
  @Override
  public void update(Long userId, DeckUpdateRequest deckUpdateRequest, Long deckId) {

    // デッキエンティティを作成
    DeckUpdateEntity deckUpdateEntity =
        new DeckUpdateEntity(userId, deckUpdateRequest.getDeckName(),
            deckUpdateRequest.getNativeLanguageId(), deckUpdateRequest.getLearningLanguageId());

    deckRepository.update(deckUpdateEntity, deckId);
  }

  /**
   * デッキを削除する
   *
   * @param deckId デッキID
   */
  @Override
  public void delete(Long deckId) {
    deckRepository.delete(deckId);
  }
}
