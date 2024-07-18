package com.example.iruka_backend.service;

import java.util.List;

import com.example.iruka_backend.entity.Deck;

public interface DeckService {
	List<Deck> getAllDecks();
	Deck save(Deck deck);
	Deck updateDeckName(Long id, String newDeckName);
}
