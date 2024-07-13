package com.example.iruka_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.iruka_backend.entity.Deck;
import com.example.iruka_backend.service.DeckService;

@RestController
@RequestMapping("/api/decks")
public class DeckController {
	
	@Autowired
	private DeckService deckService;
	
	@GetMapping
	public List<Deck> getAllDecks() {
		return deckService.getAllDecks();
	}
}
