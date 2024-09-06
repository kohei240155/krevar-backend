package com.example.iruka_backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
import com.example.iruka_backend.entity.UserEntity;
import com.example.iruka_backend.repository.UserRepository;
import com.example.iruka_backend.requestdto.DeckCreateRequest;
import com.example.iruka_backend.requestdto.DeckUpdateRequest;
import com.example.iruka_backend.responsedto.DeckListResponse;
import com.example.iruka_backend.service.DeckService;

@RestController
@RequestMapping("/api/deck")
@CrossOrigin(origins = "http://localhost:3000")
public class DeckController {

  private static final Logger logger = LoggerFactory.getLogger(DeckController.class);

  @Autowired
  private DeckService deckService;

  @Autowired
  private UserRepository userRepository;

  @GetMapping
  public DeckListResponse getAllDecks(@RequestParam(name = "page", defaultValue = "0") int page,
      @RequestParam(name = "size", defaultValue = "10") int size) {

    logger.info("------------- デッキ取得API開始 -------------");

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = null;
    if (authentication.getPrincipal() instanceof UserDetails) {
      username = ((UserDetails) authentication.getPrincipal()).getUsername();
    }

    UserEntity user = userRepository.findByEmail(username);
    int userId = user.getId().intValue();

    DeckListResponse decks = deckService.getDecksByUserId(userId, page, size);

    logger.info("------------- デッキ取得API終了 -------------");

    return decks;
  }

  @PostMapping
  public String createDeck(@RequestBody DeckCreateRequest deckCreateRequest) {

    logger.info("------------- デッキ作成API開始 -------------");

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = null;
    if (authentication.getPrincipal() instanceof UserDetails) {
      username = ((UserDetails) authentication.getPrincipal()).getUsername();
    }

    UserEntity user = userRepository.findByEmail(username);
    int userId = user.getId().intValue();

    deckService.checkUserId(userId, deckCreateRequest.getUserId());

    deckService.save(deckCreateRequest);

    logger.info("------------- デッキ作成API終了 -------------");

    return "デッキの作成に成功しました";
  }

  @PutMapping("/{deckId}")
  public String updateDeck(@PathVariable("deckId") Long deckId,
      @RequestBody DeckUpdateRequest deckUpdateRequest) {

    logger.info("------------- デッキ更新API開始 -------------");

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = null;
    if (authentication.getPrincipal() instanceof UserDetails) {
      username = ((UserDetails) authentication.getPrincipal()).getUsername();
    }

    UserEntity user = userRepository.findByEmail(username);
    int userId = user.getId().intValue();

    deckService.checkUserId(userId, deckUpdateRequest.getUserId());

    deckService.update(deckUpdateRequest, deckId);

    logger.info("------------- デッキ更新API終了 -------------");

    return "デッキの更新に成功しました";
  }

  @DeleteMapping("/{deckId}")
  public String deleteDeck(@PathVariable("deckId") Long deckId) {

    logger.info("------------- デッキ削除API開始 -------------");

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = null;
    if (authentication.getPrincipal() instanceof UserDetails) {
      username = ((UserDetails) authentication.getPrincipal()).getUsername();
    }

    UserEntity user = userRepository.findByEmail(username);
    int userId = user.getId().intValue();

    deckService.checkUserId(userId, deckService.getUserIdByDeckId(deckId));

    deckService.delete(deckId);

    logger.info("------------- デッキ削除API終了 -------------");

    return "デッキの削除に成功しました";
  }
}
