package com.example.iruka_backend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.iruka_backend.entity.DeckEntity;
import com.example.iruka_backend.repository.DeckRepository;
import com.example.iruka_backend.service.DeckService;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class DeckServiceImpl implements DeckService {

	@Autowired
	private DeckRepository deckRepository;

	@Override
	public List<DeckEntity> getAllDecks() {
		return deckRepository.findByDeletedAtIsNull(); // ソフトデリートされたデッキを除外
	}

	@Override
	public DeckEntity save(DeckEntity deck) {
		return deckRepository.save(deck);
	}

	@Override
	public DeckEntity updateDeckName(Long id, String newDeckName) {
		DeckEntity deck = deckRepository.findById(id).orElseThrow(() -> new RuntimeException("Deck not found"));
		deck.setDeckName(newDeckName);
		return deckRepository.save(deck);
	}

	@Override
	public void deleteDeck(Long id) {
		DeckEntity deck = deckRepository.findById(id).orElseThrow(() -> new RuntimeException("Deck not found"));
		deck.setDeletedAt(Timestamp.valueOf(LocalDateTime.now())); // 削除フラグを設定
		deckRepository.save(deck);
	}

	// ページネーション対応のメソッドを実装
	@Override
	public Page<DeckEntity> getDecks(Pageable pageable) {
		return deckRepository.findAllByDeletedAtIsNull(pageable); // ソフトデリートされたデッキを除外
	}

	@Override
	public void softDeleteDeck(Long id) {
		DeckEntity deck = deckRepository.findById(id).orElseThrow(() -> new RuntimeException("Deck not found"));
		deck.setDeletedAt(Timestamp.valueOf(LocalDateTime.now())); // 削除フラグを設定
		deckRepository.save(deck);
	}

	@Override
	public long countActiveDecks() {
		return deckRepository.countByDeletedAtIsNull(); // 追加: 有効なデッキのカウント
	}
}