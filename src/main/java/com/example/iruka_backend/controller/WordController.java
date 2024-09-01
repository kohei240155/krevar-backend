package com.example.iruka_backend.controller;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import com.example.iruka_backend.requestdto.ImageUploadRequest;
import com.example.iruka_backend.requestdto.WordUpdateRequest;
import com.example.iruka_backend.responsedto.WordCreatedResponse;
import com.example.iruka_backend.responsedto.WordListResponse;
import com.example.iruka_backend.responsedto.WordUpdatedResponse;
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

  @GetMapping("/deck/{deckId}")
  public WordListResponse getAllWords(@RequestParam(name = "page", defaultValue = "0") int page,
      @RequestParam(name = "size", defaultValue = "10") int size) {
    Pageable pageable = PageRequest.of(page, size);
    Page<WordEntity> words = wordService.getWords(pageable);
    long count = wordService.countActiveWords();
    return new WordListResponse(words.getContent(), count);
  }

  @GetMapping("/{wordId}")
  public WordEntity getWordById(@PathVariable("wordId") Long wordId) {
    return wordService.getWordById(wordId).orElse(null);
  }

  @PostMapping("/{deckId}")
  public WordCreatedResponse createWord(@PathVariable("deckId") Long deckId,
      @RequestBody WordEntity word) {
    word.setDeckId(deckId);
    word.setReviewIntervalId(1L);
    word.setNextPracticeDate(LocalDate.now());
    word.setCorrectCount(0L);
    word.setIncorrectCount(0L);
    word.setIsNormalModeCorrect(false);
    word.setIsExtraModeCorrect(false);
    word.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
    word.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
    WordEntity createdWord = wordService.save(word);
    return new WordCreatedResponse(createdWord.getId(), "Word created successfully");
  }

  @PostMapping("/upload-image")
  public String uploadImage(@RequestBody ImageUploadRequest imageUploadRequest) {
    try {
      String imagePath = imageUploadRequest.getImagePath();
      Long wordId = imageUploadRequest.getWordId();

      if (imagePath == null) {
        throw new IllegalArgumentException("imagePath must be provided");
      }

      // OpenAI APIで生成された画像をローカルディレクトリに保存する
      String savedImagePath = imageService.saveImageFromOpenAI(imagePath);

      // 画像の保存パスを更新
      WordEntity word =
          wordService.getWordById(wordId).orElseThrow(() -> new RuntimeException("Word not found"));
      word.setImageUrl(savedImagePath);
      wordService.save(word);

      return "Image uploaded successfully: " + savedImagePath;
    } catch (Exception e) {
      return "Failed to upload image: " + e.getMessage();
    }
  }

  @PutMapping("/{wordId}")
  public WordUpdatedResponse updateWord(@PathVariable("wordId") Long wordId,
      @RequestBody WordUpdateRequest wordUpdateRequest) {
    WordEntity word =
        wordService.getWordById(wordId).orElseThrow(() -> new RuntimeException("Word not found"));
    word.setOriginalText(wordUpdateRequest.getOriginalText());
    word.setTranslatedText(wordUpdateRequest.getTranslatedText());
    word.setImageUrl(wordUpdateRequest.getImageUrl());
    word.setDeckId(wordUpdateRequest.getDeckId());
    word.setNuanceText(wordUpdateRequest.getNuanceText());
    word.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
    WordEntity updatedWord = wordService.update(word);
    return new WordUpdatedResponse(updatedWord.getId(), "Word updated successfully");
  }
}
