package com.example.iruka_backend.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.iruka_backend.entity.WordEntity;
import com.example.iruka_backend.repository.WordRepository;
import com.example.iruka_backend.service.WordService;

@Service
public class WordServiceImpl implements WordService {

  @Autowired
  private WordRepository wordRepository;

  @Override
  public List<WordEntity> getWordsByDeckId(Long deckId) {
    return wordRepository.findWordsByDeckId(deckId); // 修正
  }

  @Override
  public WordEntity save(WordEntity word) {
    return wordRepository.save(word);
  }

  @Override
  public Optional<WordEntity> getWordById(Long wordId) {
    return wordRepository.findById(wordId);
  }

  @Override
  public WordEntity update(WordEntity word) {
    return wordRepository.update(word);
  }

  @Override
  public Page<WordEntity> getWords(Pageable pageable) {
    return wordRepository.findAllByDeletedAtIsNull(pageable);
  }

  @Override
  public long countActiveWords() {
    return wordRepository.countByDeletedAtIsNull();
  }
}
