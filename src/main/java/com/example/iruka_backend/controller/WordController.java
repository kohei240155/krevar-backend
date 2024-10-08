package com.example.iruka_backend.controller;

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
import com.example.iruka_backend.requestdto.WordCreateRequest;
import com.example.iruka_backend.requestdto.WordUpdateRequest;
import com.example.iruka_backend.responsedto.WordListResponse;
import com.example.iruka_backend.responsedto.WordResponse;
import com.example.iruka_backend.security.JwtTokenProvider;
import com.example.iruka_backend.service.WordService;
import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/api")
public class WordController {

  @Autowired
  private WordService wordService;

  @Autowired
  private JwtTokenProvider jwtTokenProvider;

  private static final Logger logger = LoggerFactory.getLogger(WordController.class);

  /**
   * デッキに紐づく単語一覧取得
   *
   * @param httpServletRequest
   * @param page
   * @param size
   * @param userId
   * @param deckId
   * @return 単語一覧
   */
  @GetMapping("/user/{userId}/deck/{deckId}")
  public WordListResponse getWordsByDeckId(HttpServletRequest httpServletRequest,
      @RequestParam(name = "page", defaultValue = "0") Long page,
      @RequestParam(name = "size", defaultValue = "10") Long size,
      @PathVariable("userId") Long userId, @PathVariable("deckId") Long deckId) {

    logger.info("------------- 単語一覧取得API開始 -------------");

    jwtTokenProvider.validateToken(httpServletRequest.getHeader("Authorization"));

    WordListResponse wordListResponse = wordService.getWordListByDeckId(deckId, page, size);

    logger.info("------------- 単語一覧取得API終了 -------------");

    return wordListResponse;
  }

  /**
   * 単語登録
   *
   * @param httpServletRequest
   * @param wordRegisterRequest
   */
  @PostMapping("/word")
  public void createWord(HttpServletRequest httpServletRequest,
      @RequestBody WordCreateRequest wordCreateRequest) {

    logger.info("------------- 単語登録API開始 -------------");

    jwtTokenProvider.validateToken(httpServletRequest.getHeader("Authorization"));

    wordService.save(wordCreateRequest);

    logger.info("------------- 単語登録API終了 -------------");
  }

  /**
   * 単語更新
   *
   * @param httpServletRequest
   * @param wordUpdateRequest
   */
  @PutMapping("/word")
  public void updateWord(HttpServletRequest httpServletRequest,
      @RequestBody WordUpdateRequest wordUpdateRequest) {

    logger.info("------------- 単語更新API開始 -------------");

    jwtTokenProvider.validateToken(httpServletRequest.getHeader("Authorization"));

    wordService.update(wordUpdateRequest);

    logger.info("------------- 単語更新API終了 -------------");
  }

  /**
   * 単語取得
   *
   * @param httpServletRequest
   * @param userId
   * @param wordId
   * @return
   */
  @GetMapping("/user/{userId}/word/{wordId}")
  public WordResponse getWordById(HttpServletRequest httpServletRequest,
      @PathVariable("userId") Long userId, @PathVariable("wordId") Long wordId) {

    logger.info("------------- 単語取得API開始 -------------");

    jwtTokenProvider.validateToken(httpServletRequest.getHeader("Authorization"));

    WordResponse wordResponse = wordService.getWordById(wordId);

    logger.info("------------- 単語取得API終了 -------------");

    return wordResponse;
  }

  /**
   * 単語削除
   *
   * @param httpServletRequest
   * @param userId
   * @param wordId
   */
  @DeleteMapping("/user/{userId}/word/{wordId}")
  public void deleteWord(HttpServletRequest httpServletRequest, @PathVariable("userId") Long userId,
      @PathVariable("wordId") Long wordId) {

    logger.info("------------- 単語削除API開始 -------------");

    jwtTokenProvider.validateToken(httpServletRequest.getHeader("Authorization"));

    wordService.delete(wordId);

    logger.info("------------- 単語削除API終了 -------------");
  }

}
