package com.example.krevar_backend.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.krevar_backend.requestdto.DeckCreateRequest;
import com.example.krevar_backend.requestdto.DeckUpdateRequest;
import com.example.krevar_backend.responsedto.DeckInfo;
import com.example.krevar_backend.responsedto.DeckListResponse;
import com.example.krevar_backend.security.JwtAuthorizationFilter;
import com.example.krevar_backend.security.JwtTokenProvider;
import com.example.krevar_backend.service.DeckService;

@RestController
@RequestMapping("/api/deck")
public class DeckController {

  @Autowired
  private DeckService deckService;

  @Autowired
  private JwtTokenProvider jwtTokenProvider;

  @Autowired
  private JwtAuthorizationFilter jwtAuthorizationFilter;

  private static final Logger logger = LoggerFactory.getLogger(DeckController.class);

  /**
   * デッキ一覧取得
   *
   * @param httpServletRequest
   * @param userId
   * @param page
   * @param size
   * @return デッキ一覧
   */
  @GetMapping
  public DeckListResponse getDeckList(HttpServletRequest httpServletRequest,
      @RequestParam(name = "page", defaultValue = "0") Long page,
      @RequestParam(name = "size", defaultValue = "10") Long size) {

    logger.info("------------- Get Deck List API Start -------------");

    // ユーザーIDを取得
    String token = jwtAuthorizationFilter.extractToken(httpServletRequest);
    Long userId = jwtTokenProvider.getUserIdFromToken(token);

    logger.info("Request Info: UserId={}, Page={}, Size={}", userId, page, size);

    DeckListResponse decks = deckService.getDeckList(userId, page, size);

    logger.info("------------- Get Deck List API End -------------");

    return decks;
  }

  /**
   * デッキ作成
   *
   * @param httpServletRequest
   * @param deckCreateRequest
   * @return デッキ作成成功メッセージ
   */
  @PostMapping
  public String createDeck(HttpServletRequest httpServletRequest,
      @RequestBody DeckCreateRequest deckCreateRequest) {

    logger.info("------------- Create Deck API Start -------------");

    // ユーザーIDを取得
    String token = jwtAuthorizationFilter.extractToken(httpServletRequest);
    Long userId = jwtTokenProvider.getUserIdFromToken(token);

    logger.info("Request Info: UserId={}, DeckCreateRequest={}", userId, deckCreateRequest);

    deckService.save(userId, deckCreateRequest);

    logger.info("------------- Create Deck API End -------------");

    return "Deck create success";
  }

  /**
   * デッキIDからデッキを取得
   *
   * @param deckId
   * @return デッキ
   */
  @GetMapping("/{deckId}")
  public DeckInfo getDeckById(HttpServletRequest httpServletRequest,
      @PathVariable("deckId") Long deckId) {

    logger.info("------------- Get Deck By ID API Start -------------");

    // デッキを取得
    DeckInfo deck = deckService.getDeck(deckId);

    logger.info("------------- Get Deck By ID API End -------------");

    return deck;
  }

  /**
   * デッキ更新
   *
   * @param httpServletRequest
   * @param deckId
   * @param deckUpdateRequest
   * @return デッキ更新成功メッセージ
   */
  @PutMapping("/{deckId}")
  public String updateDeck(HttpServletRequest httpServletRequest,
      @PathVariable("deckId") Long deckId, @RequestBody DeckUpdateRequest deckUpdateRequest) {

    logger.info("------------- Update Deck API Start -------------");

    // ユーザーIDを取得
    String token = jwtAuthorizationFilter.extractToken(httpServletRequest);
    Long userId = jwtTokenProvider.getUserIdFromToken(token);

    deckService.update(userId, deckUpdateRequest, deckId);

    logger.info("------------- Update Deck API End -------------");

    return "Deck update success";
  }

  /**
   * デッキ削除
   *
   * @param httpServletRequest
   * @param deckId
   * @return デッキ削除成功メッセージ
   */
  @DeleteMapping("/{deckId}")
  public String deleteDeck(HttpServletRequest httpServletRequest,
      @PathVariable("deckId") Long deckId) {

    logger.info("------------- Delete Deck API Start -------------");

    deckService.delete(deckId);

    logger.info("------------- Delete Deck API End -------------");

    return "Deck delete success";
  }
}
