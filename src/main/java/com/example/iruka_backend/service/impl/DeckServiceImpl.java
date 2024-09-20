package com.example.iruka_backend.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.iruka_backend.entity.DeckCreateEntity;
import com.example.iruka_backend.entity.DeckEntity;
import com.example.iruka_backend.entity.DeckUpdateEntity;
import com.example.iruka_backend.repository.DeckRepository;
import com.example.iruka_backend.repository.WordRepository;
import com.example.iruka_backend.requestdto.DeckCreateRequest;
import com.example.iruka_backend.requestdto.DeckUpdateRequest;
import com.example.iruka_backend.responsedto.DeckInfo;
import com.example.iruka_backend.responsedto.DeckListResponse;
import com.example.iruka_backend.service.DeckService;

@Service
public class DeckServiceImpl implements DeckService {

  @Autowired
  private DeckRepository deckRepository;

  @Autowired
  private WordRepository wordRepository;

  /**
   * ユーザーIDに紐づくデッキを取得する
   *
   * @param userId ユーザーID
   * @return デッキリスト
   */
  @Override
  public DeckListResponse getDecksByUserId(Long userId, Long page, Long size) {

    // デッキを取得
    List<DeckEntity> decks = deckRepository.findByUserId(userId);

    List<DeckInfo> deckInfoList = new ArrayList<>();

    for (DeckEntity deck : decks) {
      DeckInfo deckInfo = new DeckInfo();
      deckInfo.setId(deck.getId());
      deckInfo.setDeckName(deck.getDeckName());
      int progress = wordRepository.getProgressByDeckId(deck.getId());
      deckInfo.setProgress(progress);
      deckInfoList.add(deckInfo);
    }

    DeckListResponse response = new DeckListResponse(deckInfoList);

    // progressの値が多い順に並び替え
    response.getDeckInfo().sort(Comparator.comparingInt(DeckInfo::getProgress).reversed());

    // ページネーション
    Long offset = page * size;
    Long limit = size;
    List<DeckInfo> paginatedDeckInfo =
        response.getDeckInfo().stream().skip(offset).limit(limit).collect(Collectors.toList());

    response.setDeckInfo(paginatedDeckInfo);

    return response;
  }

  /**
   * デッキを保存する
   *
   * @param deckCreateRequest デッキリクエスト
   */
  @Override
  public void save(DeckCreateRequest deckCreateRequest) {

    // デッキエンティティを作成
    DeckCreateEntity deckCreateEntity =
        new DeckCreateEntity(deckCreateRequest.getUserId(), deckCreateRequest.getDeckName());

    // デッキを保存
    deckRepository.save(deckCreateEntity);
  }

  /**
   * デッキを更新する
   *
   * @param deckUpdateRequest デッキ更新リクエスト
   */
  @Override
  public void update(DeckUpdateRequest deckUpdateRequest, Long deckId) {

    // デッキエンティティを作成
    DeckUpdateEntity deckUpdateEntity =
        new DeckUpdateEntity(deckUpdateRequest.getUserId(), deckUpdateRequest.getDeckName());

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
