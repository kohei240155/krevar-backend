package com.example.iruka_backend.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.iruka_backend.entity.DeckEntity;

public interface DeckService {
	List<DeckEntity> getAllDecks();
	DeckEntity save(DeckEntity deck);
	DeckEntity updateDeckName(Long id, String newDeckName);
	void deleteDeck(Long id);
	// ページネーション対応のメソッドを追加
	Page<DeckEntity> getDecks(Pageable pageable);
}