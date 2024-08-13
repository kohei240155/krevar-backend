package com.example.iruka_backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.iruka_backend.entity.WordEntity;

public interface WordService {
	List<WordEntity> getWordsByDeckId(Long deckId);
	WordEntity save(WordEntity word);
	Optional<WordEntity> getWordById(Long wordId);
	WordEntity update(WordEntity word);
	Page<WordEntity> getWords(Pageable pageable); // ページネーション対応
	long countActiveWords(); // 有効なWordのカウント
}