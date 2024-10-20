package com.example.krevar_backend.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.krevar_backend.requestdto.ExtraQuizResultUpdateRequest;
import com.example.krevar_backend.requestdto.NormalQuizResultUpdateRequest;
import com.example.krevar_backend.responsedto.QuizResponse;
import com.example.krevar_backend.security.JwtTokenProvider;
import com.example.krevar_backend.service.QuizService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class QuizController {

  @Autowired
  private QuizService quizService;

  @Autowired
  private JwtTokenProvider jwtTokenProvider;

  private static final org.slf4j.Logger logger = LoggerFactory.getLogger(QuizController.class);

  /**
   * ノーマルクイズ取得
   *
   * @param httpServletRequest
   * @param userId
   * @param deckId
   * @return ノーマルクイズ
   */
  @GetMapping("/user/{userId}/normal-quiz/deck/{deckId}")
  public QuizResponse getNormalQuiz(HttpServletRequest httpServletRequest,
      @PathVariable("userId") Long userId, @PathVariable("deckId") Long deckId) {

    logger.info("------------- ノーマルクイズ取得API開始 -------------");

    jwtTokenProvider.validateToken(httpServletRequest.getHeader("Authorization"));

    QuizResponse quizResponse = quizService.getNormalQuiz(deckId);

    logger.info("------------- ノーマルクイズ取得API終了 -------------");

    return quizResponse;
  }

  /**
   * ノーマルクイズ更新
   *
   * @param httpServletRequest
   * @param request
   */
  @PutMapping("/normal-quiz")
  public void updateNormalQuiz(HttpServletRequest httpServletRequest,
      @RequestBody NormalQuizResultUpdateRequest request) {

    logger.info("------------- ノーマルクイズ更新API開始 -------------");

    jwtTokenProvider.validateToken(httpServletRequest.getHeader("Authorization"));

    quizService.updateNormalQuiz(request);

    logger.info("------------- ノーマルクイズ更新API終了 -------------");

  }

  /**
   * エクストラクイズ取得
   *
   * @param httpServletRequest
   * @param userId
   * @param deckId
   * @return エクストラクイズ
   */
  @GetMapping("/user/{userId}/extra-quiz/deck/{deckId}")
  public QuizResponse getExtraQuiz(HttpServletRequest httpServletRequest,
      @PathVariable("userId") Long userId, @PathVariable("deckId") Long deckId) {

    logger.info("------------- エクストラクイズ取得API開始 -------------");

    jwtTokenProvider.validateToken(httpServletRequest.getHeader("Authorization"));

    QuizResponse quizResponse = quizService.getExtraQuiz(deckId);

    logger.info("------------- エクストラクイズ取得API終了 -------------");

    return quizResponse;
  }

  /**
   * エクストラクイズ更新
   *
   * @param httpServletRequest
   * @param request
   */
  @PutMapping("/extra-quiz")
  public void updateExtraQuiz(HttpServletRequest httpServletRequest,
      @RequestBody ExtraQuizResultUpdateRequest request) {

    logger.info("------------- エクストラクイズ更新API開始 -------------");

    jwtTokenProvider.validateToken(httpServletRequest.getHeader("Authorization"));

    quizService.updateExtraQuiz(request);

    logger.info("------------- エクストラクイズ更新API終了 -------------");
  }

  /**
   * エクストラクイズリセット
   *
   * @param httpServletRequest
   * @param userId
   * @param deckId
   */
  @PutMapping("/user/{userId}/extra-quiz/reset/deck/{deckId}")
  public void resetExtraQuiz(HttpServletRequest httpServletRequest,
      @PathVariable("userId") Long userId, @PathVariable("deckId") Long deckId) {

    logger.info("------------- エクストラクイズリセットAPI開始 -------------");

    jwtTokenProvider.validateToken(httpServletRequest.getHeader("Authorization"));

    quizService.resetExtraQuiz(deckId);

    logger.info("------------- エクストラクイズリセットAPI終了 -------------");
  }
}
