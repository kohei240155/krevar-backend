package com.example.iruka_backend.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.iruka_backend.entity.WordEntity;
import com.example.iruka_backend.service.QuizService;

@RestController
@RequestMapping("/api/quiz")
@CrossOrigin(origins = "http://localhost:3000")
public class QuizController {

  @Autowired
  private QuizService quizService;

  @GetMapping("/normal/{deckId}")
  public Map<String, Object> getRandomQuestionByDeckId(@PathVariable("deckId") Long deckId) {
    Optional<WordEntity> randomQuestion = quizService.getRandomQuestionByDeckId(deckId);
    Long todayNormalQuestionCount = quizService.getTodayNormalQuestionCountByDeckId(deckId);

    Map<String, Object> response = new HashMap<>();
    response.put("randomQuestion", randomQuestion.orElse(null));
    response.put("todayNormalQuestionCount", todayNormalQuestionCount);

    return response;
  }

  @GetMapping("/extra/{deckId}")
  public Map<String, Object> getExtraQuestionByDeckId(@PathVariable("deckId") Long deckId,
      @RequestParam(value = "reset", required = false) Boolean reset) {
    if (Boolean.TRUE.equals(reset)) {
      quizService.resetExtraModeCorrectByDeckId(deckId);
    }
    Optional<WordEntity> extraQuestion = quizService.getRandomExtraQuestionByDeckId(deckId); // 修正
    Long todayExtraQuestionCount = quizService.getTodayExtraQuestionCountByDeckId(deckId);

    Map<String, Object> response = new HashMap<>();
    response.put("extraQuestion", extraQuestion.orElse(null));
    response.put("todayExtraQuestionCount", todayExtraQuestionCount);

    return response;
  }

  @PostMapping("/normal/answer/{wordId}")
  public void submitAnswer(@PathVariable("wordId") Long wordId,
      @RequestBody Map<String, Boolean> request) {
    Boolean isNormalModeCorrect = request.get("isNormalModeCorrect");
    quizService.updateWordIsNormalModeCorrect(wordId, isNormalModeCorrect);
  }

  @PostMapping("/extra/answer/{wordId}")
  public void submitExtraAnswer(@PathVariable("wordId") Long wordId,
      @RequestBody Map<String, Boolean> request) {
    Boolean isExtraModeCorrect = request.get("isExtraModeCorrect");
    quizService.updateWordIsExtraModeCorrect(wordId, isExtraModeCorrect);
  }

  @PutMapping("/extra/{deckId}/reset")
  public void resetExtraModeCorrectByDeckId(@PathVariable("deckId") Long deckId) {
    quizService.resetExtraModeCorrectByDeckId(deckId);
  }
}
