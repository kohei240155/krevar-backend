package com.example.iruka_backend.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.iruka_backend.requestdto.ExtraQuizResultUpdateRequest;
import com.example.iruka_backend.requestdto.NormalQuizResultUpdateRequest;
import com.example.iruka_backend.responsedto.QuizResponse;
import com.example.iruka_backend.service.QuizService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class QuizController {

  @Autowired
  private QuizService quizService;

  private static final org.slf4j.Logger logger = LoggerFactory.getLogger(QuizController.class);

  /**
   * ノーマルクイズ取得
   *
   * @param request
   * @return ノーマルクイズ
   */
  @GetMapping("/user/{userId}/normal-quiz/deck/{deckId}")
  public QuizResponse getNormalQuiz(@PathVariable("userId") Long userId,
      @PathVariable("deckId") Long deckId) {

    logger.info("------------- ノーマルクイズ取得API開始 -------------");

    quizService.verifyUser(userId);

    QuizResponse quizResponse = quizService.getNormalQuiz(deckId);

    logger.info("------------- ノーマルクイズ取得API終了 -------------");

    return quizResponse;
  }

  /**
   * ノーマルクイズ更新
   *
   * @param request
   */
  @PutMapping("/normal-quiz")
  public void updateNormalQuiz(@RequestBody NormalQuizResultUpdateRequest request) {

    logger.info("------------- ノーマルクイズ更新API開始 -------------");

    quizService.verifyUser(request.getUserId());

    quizService.updateNormalQuiz(request);

    logger.info("------------- ノーマルクイズ更新API終了 -------------");

  }

  /**
   * エクストラクイズ取得
   *
   * @param request
   * @return エクストラクイズ
   */
  @GetMapping("/user/{userId}/extra-quiz/deck/{deckId}")
  public QuizResponse getExtraQuiz(@PathVariable("userId") Long userId,
      @PathVariable("deckId") Long deckId) {

    logger.info("------------- エクストラクイズ取得API開始 -------------");

    quizService.verifyUser(userId);

    QuizResponse quizResponse = quizService.getExtraQuiz(deckId);

    logger.info("------------- エクストラクイズ取得API終了 -------------");

    return quizResponse;
  }

  /**
   * エクストラクイズ更新
   *
   * @param request
   */
  @PutMapping("/extra-quiz")
  public void updateExtraQuiz(@RequestBody ExtraQuizResultUpdateRequest request) {

    logger.info("------------- エクストラクイズ更新API開始 -------------");

    quizService.verifyUser(request.getUserId());

    quizService.updateExtraQuiz(request);

    logger.info("------------- エクストラクイズ更新API終了 -------------");
  }

  /**
   * エクストラクイズリセット
   *
   * @param deckId
   */
  @PutMapping("/user/{userId}/extra-quiz/reset/deck/{deckId}")
  public void resetExtraQuiz(@PathVariable("userId") Long userId,
      @PathVariable("deckId") Long deckId) {

    logger.info("------------- エクストラクイズリセットAPI開始 -------------");

    quizService.verifyUser(userId);

    quizService.resetExtraQuiz(deckId);

    logger.info("------------- エクストラクイズリセットAPI終了 -------------");
  }
}
