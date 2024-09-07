package com.example.iruka_backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.iruka_backend.requestdto.DeckCreateRequest;
import com.example.iruka_backend.requestdto.DeckUpdateRequest;
import com.example.iruka_backend.responsedto.DeckListResponse;
import com.example.iruka_backend.service.DeckService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class DeckController {

  @Autowired
  private DeckService deckService;

  private static final Logger logger = LoggerFactory.getLogger(DeckController.class);

  /**
   * デッキ一覧取得
   *
   * @param userId
   * @param page
   * @param size
   * @return デッキ一覧
   */
  @GetMapping("/user/{userId}/deck")
  public DeckListResponse getDeckList(@PathVariable("userId") Long userId,
      @RequestParam(name = "page", defaultValue = "0") Long page,
      @RequestParam(name = "size", defaultValue = "10") Long size) {

    logger.info("------------- デッキ一覧取得API開始 -------------");

    deckService.verifyUser(userId);

    DeckListResponse decks = deckService.getDecksByUserId(userId, page, size);

    logger.info("------------- デッキ一覧取得API終了 -------------");

    return decks;
  }

  /**
   * デッキ作成
   *
   * @param deckCreateRequest
   * @return デッキ作成成功メッセージ
   */
  @PostMapping("/deck")
  public String createDeck(@RequestBody DeckCreateRequest deckCreateRequest) {

    logger.info("------------- デッキ作成API開始 -------------");

    deckService.verifyUser(deckCreateRequest.getUserId());

    deckService.save(deckCreateRequest);

    logger.info("------------- デッキ作成API終了 -------------");

    return "デッキの作成に成功しました";
  }

  /**
   * デッキ更新
   *
   * @param deckId
   * @param deckUpdateRequest
   * @return デッキ更新成功メッセージ
   */
  @PutMapping("/deck/{deckId}")
  public String updateDeck(@PathVariable("deckId") Long deckId,
      @RequestBody DeckUpdateRequest deckUpdateRequest) {

    logger.info("------------- デッキ更新API開始 -------------");

    deckService.verifyUser(deckUpdateRequest.getUserId());

    deckService.update(deckUpdateRequest, deckId);

    logger.info("------------- デッキ更新API終了 -------------");

    return "デッキの更新に成功しました";
  }

  /**
   * デッキ削除
   *
   * @param deckId
   * @return デッキ削除成功メッセージ
   */
  @DeleteMapping("/user/{userId}/deck/{deckId}")
  public String deleteDeck(@PathVariable("userId") Long userId,
      @PathVariable("deckId") Long deckId) {

    logger.info("------------- デッキ削除API開始 -------------");

    deckService.verifyUser(userId);

    deckService.delete(deckId);

    logger.info("------------- デッキ削除API終了 -------------");

    return "デッキの削除に成功しました";
  }
}
