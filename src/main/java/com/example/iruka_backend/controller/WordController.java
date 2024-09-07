package com.example.iruka_backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.iruka_backend.responsedto.WordListResponse;
import com.example.iruka_backend.service.ImageService;
import com.example.iruka_backend.service.WordService;

@RestController
@RequestMapping("/api/word")
@CrossOrigin(origins = "http://localhost:3000")
public class WordController {

  @Autowired
  private WordService wordService;

  @Autowired
  private ImageService imageService;

  private static final Logger logger = LoggerFactory.getLogger(WordController.class);

  @GetMapping("/{userId}/{deckId}")
  public WordListResponse getWordsByDeckId(
      @RequestParam(name = "page", defaultValue = "0") Long page,
      @RequestParam(name = "size", defaultValue = "10") Long size,
      @PathVariable("userId") Long userId, @PathVariable("deckId") Long deckId) {

    logger.info("------------- 単語一覧取得API開始 -------------");

    wordService.verifyUser(userId);

    logger.info("------------- 単語一覧取得API終了 -------------");
    return wordService.getWordListByDeckId(deckId, page, size);
  }

  // @PostMapping
  // public WordEntity createWord(@RequestBody WordEntity word) {

  // logger.info("------------- 単語作成API開始 -------------");

  // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
  // String username = null;
  // if (authentication.getPrincipal() instanceof UserDetails) {
  // username = ((UserDetails) authentication.getPrincipal()).getUsername();
  // }

  // UserEntity user = userRepository.findByEmail(username);
  // int userId = user.getId().intValue();

  // wordService.checkUserId(userId, word.getDeckId());

  // logger.info("------------- 単語作成API終了 -------------");

  // return wordService.save(word);
  // }

  // @PutMapping("/{wordId}")
  // public WordEntity updateWord(@PathVariable("wordId") Long wordId, @RequestBody WordEntity word)
  // {

  // logger.info("------------- 単語更新API開始 -------------");

  // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
  // String username = null;
  // if (authentication.getPrincipal() instanceof UserDetails) {
  // username = ((UserDetails) authentication.getPrincipal()).getUsername();
  // }

  // logger.info("------------- 単語更新API終了 -------------");
  // return wordService.update(word);
  // }

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

  // @GetMapping("/deck/{deckId}")
  // public WordListResponse getAllWords(@RequestParam(name = "page", defaultValue = "0") int page,
  // @RequestParam(name = "size", defaultValue = "10") int size) {
  // Pageable pageable = PageRequest.of(page, size);
  // Page<WordEntity> words = wordService.getWords(pageable);
  // long count = wordService.countActiveWords();
  // return new WordListResponse(words.getContent(), count);
  // }

  // @GetMapping("/{wordId}")
  // public WordEntity getWordById(@PathVariable("wordId") Long wordId) {
  // return wordService.getWordById(wordId).orElse(null);
  // }

  // @PostMapping("/{deckId}")
  // public WordCreatedResponse createWord(@PathVariable("deckId") Long deckId,
  // @RequestBody WordEntity word) {
  // word.setDeckId(deckId);
  // word.setReviewIntervalId(1L);
  // word.setNextPracticeDate(LocalDate.now());
  // word.setCorrectCount(0L);
  // word.setIncorrectCount(0L);
  // word.setIsNormalModeCorrect(false);
  // word.setIsExtraModeCorrect(false);
  // word.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
  // word.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
  // WordEntity createdWord = wordService.save(word);
  // return new WordCreatedResponse(createdWord.getId(), "Word created successfully");
  // }

  // @PostMapping("/upload-image")
  // public String uploadImage(@RequestBody ImageUploadRequest imageUploadRequest) {
  // try {
  // String imagePath = imageUploadRequest.getImagePath();
  // Long wordId = imageUploadRequest.getWordId();

  // if (imagePath == null) {
  // throw new IllegalArgumentException("imagePath must be provided");
  // }

  // // OpenAI APIで生成された画像をローカルディレクトリに保存する
  // String savedImagePath = imageService.saveImageFromOpenAI(imagePath);

  // // 画像の保存パスを更新
  // WordEntity word =
  // wordService.getWordById(wordId).orElseThrow(() -> new RuntimeException("Word not found"));
  // word.setImageUrl(savedImagePath);
  // wordService.save(word);

  // return "Image uploaded successfully: " + savedImagePath;
  // } catch (Exception e) {
  // return "Failed to upload image: " + e.getMessage();
  // }
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
