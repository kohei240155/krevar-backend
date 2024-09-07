package com.example.iruka_backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.example.iruka_backend.requestdto.WordRegisterRequest;
import com.example.iruka_backend.responsedto.WordListResponse;
import com.example.iruka_backend.service.WordService;
import com.example.iruka_backend.requestdto.WordUpdateRequest;

@RestController
@RequestMapping("/api/word")
@CrossOrigin(origins = "http://localhost:3000")
public class WordController {

  @Autowired
  private WordService wordService;

  private static final Logger logger = LoggerFactory.getLogger(WordController.class);

  @GetMapping("/{userId}/{deckId}")
  public WordListResponse getWordsByDeckId(
      @RequestParam(name = "page", defaultValue = "0") Long page,
      @RequestParam(name = "size", defaultValue = "10") Long size,
      @PathVariable("userId") Long userId, @PathVariable("deckId") Long deckId) {

    logger.info("------------- 単語一覧取得API開始 -------------");

    wordService.verifyUser(userId);

    WordListResponse wordListResponse = wordService.getWordListByDeckId(deckId, page, size);

    logger.info("------------- 単語一覧取得API終了 -------------");

    return wordListResponse;
  }

  @PostMapping
  public void registerWord(@RequestBody WordRegisterRequest wordRegisterRequest) {

    logger.info("------------- 単語登録API開始 -------------");

    wordService.verifyUser(wordRegisterRequest.getUserId());

    wordService.save(wordRegisterRequest);

    logger.info("------------- 単語登録API終了 -------------");
  }

  @PutMapping
  public void updateWord(@PathVariable("wordId") Long wordId,
      @RequestBody WordUpdateRequest wordUpdateRequest) {

    logger.info("------------- 単語更新API開始 -------------");

    wordService.verifyUser(wordUpdateRequest.getUserId());

    wordService.update(wordUpdateRequest);

    logger.info("------------- 単語更新API終了 -------------");
  }

  // @DeleteMapping("/{wordId}")
  // public void deleteWord(@PathVariable("wordId") Long wordId) {

  // logger.info("------------- 単語削除API開始 -------------");

  // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
  // String username = null;
  // if (authentication.getPrincipal() instanceof UserDetails) {
  // username = ((UserDetails) authentication.getPrincipal()).getUsername();
  // }

  // logger.info("------------- 単語削除API終了 -------------");

  // wordService.delete(wordId);
  // }

  // @PostMapping("/upload-image")
  // public String uploadImage(@RequestBody ImageUploadRequest imageUploadRequest) {

  // logger.info("------------- 画像アップロードAPI開始 -------------");

  // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
  // String username = null;
  // if (authentication.getPrincipal() instanceof UserDetails) {
  // username = ((UserDetails) authentication.getPrincipal()).getUsername();
  // }

  // UserEntity user = userRepository.findByEmail(username);
  // int userId = user.getId().intValue();

  // wordService.checkUserId(userId, word.getDeckId());

  // logger.info("------------- 画像アップロードAPI終了 -------------");

  // return imageService.uploadImage(imageUploadRequest);
  // }
  // @PutMapping("/{wordId}")
  // public WordUpdatedResponse updateWord(@PathVariable("wordId") Long wordId,
  // @RequestBody WordUpdateRequest wordUpdateRequest) {
  // WordEntity word =
  // wordService.getWordById(wordId).orElseThrow(() -> new RuntimeException("Word not found"));
  // word.setOriginalText(wordUpdateRequest.getOriginalText());
  // word.setTranslatedText(wordUpdateRequest.getTranslatedText());
  // word.setImageUrl(wordUpdateRequest.getImageUrl());
  // word.setDeckId(wordUpdateRequest.getDeckId());
  // word.setNuanceText(wordUpdateRequest.getNuanceText());
  // word.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
  // WordEntity updatedWord = wordService.update(word);
  // return new WordUpdatedResponse(updatedWord.getId(), "Word updated successfully");
  // }
}
