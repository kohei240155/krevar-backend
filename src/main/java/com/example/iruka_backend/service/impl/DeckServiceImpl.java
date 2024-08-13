package com.example.iruka_backend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.iruka_backend.entity.DeckEntity;
import com.example.iruka_backend.repository.DeckRepository;
import com.example.iruka_backend.service.DeckService;

@Service
public class DeckServiceImpl implements DeckService {

	@Autowired
	private DeckRepository deckRepository;

	@Override
	public List<DeckEntity> getAllDecks() {
		return deckRepository.findAll();
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
		deckRepository.delete(deck);
	}

	// ページネーション対応のメソッドを実装
	@Override
	public Page<DeckEntity> getDecks(Pageable pageable) {
		return deckRepository.findAll(pageable);
	}
}