package com.example.iruka_backend.controller;

import java.util.Map;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.iruka_backend.responsedto.WordResponse;
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
   * @param deckId
   * @return
   */
  @GetMapping("/user/{userId}/normal-quiz/deck/{deckId}")
  public WordResponse getNormalQuiz(@PathVariable("userId") Long userId,
      @PathVariable("deckId") Long deckId) {

    logger.info("------------- ノーマルクイズ取得API開始 -------------");

    quizService.verifyUser(userId);

    WordResponse wordResponse = quizService.getNormalQuiz(deckId);

    logger.info("------------- ノーマルクイズ取得API終了 -------------");

    return wordResponse;
  }

  /**
   * エクストラクイズ取得
   *
   * @param deckId
   * @return
   */
  @GetMapping("/extra/{deckId}")
  public Map<String, Object> getExtraQuiz(@PathVariable("deckId") Long deckId) {
    return null;
  }

  /**
   * ノーマルクイズ更新
   *
   * @param wordId
   * @param isNormalModeCorrect
   */
  @PutMapping("/normal/{wordId}")
  public void updateNormalQuiz(@PathVariable("wordId") Long wordId,
      @RequestBody Map<String, Boolean> request) {}

  /**
   * エクストラクイズ更新
   *
   * @param wordId
   * @param isExtraModeCorrect
   */
  @PutMapping("/extra/{wordId}")
  public void updateExtraQuiz(@PathVariable("wordId") Long wordId,
      @RequestBody Map<String, Boolean> request) {}

  /**
   * エクストラクイズリセット
   *
   * @param deckId
   */
  @PutMapping("/extra/{deckId}/reset")
  public void resetExtraQuiz(@PathVariable("deckId") Long deckId) {}
}
