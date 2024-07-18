package com.example.iruka_backend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.iruka_backend.entity.Deck;
import com.example.iruka_backend.repository.DeckRepository;
import com.example.iruka_backend.service.DeckService;

@Service
public class DeckServiceImpl implements DeckService {
	
	@Autowired
	private DeckRepository deckRepository;
	
	@Override
	public List<Deck> getAllDecks() {
		return deckRepository.findAll();
	}

	@Override
	public Deck save(Deck deck) {
		return deckRepository.save(deck);
	}

	@Override
	public Deck updateDeckName(Long id, String newDeckName) {
		Deck deck = deckRepository.findById(id).orElseThrow(() -> new RuntimeException("Deck not found"));
		deck.setDeckName(newDeckName);
		return deckRepository.save(deck);
	}

	@Override
	public void deleteDeck(Long id) {
		Deck deck = deckRepository.findById(id).orElseThrow(() -> new RuntimeException("Deck not found"));
		deckRepository.delete(deck);
	}
}
