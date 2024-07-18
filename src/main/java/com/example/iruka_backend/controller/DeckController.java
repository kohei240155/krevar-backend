package com.example.iruka_backend.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.iruka_backend.entity.Deck;
import com.example.iruka_backend.service.DeckService;

@RestController
@RequestMapping("/api/decks")
@CrossOrigin(origins = "http://localhost:3000")
public class DeckController {
	
	private static final Logger logger = LoggerFactory.getLogger(DeckController.class);
	
	@Autowired
	private DeckService deckService;
	
	@GetMapping
	public List<Deck> getAllDecks() {
		return deckService.getAllDecks();
	}
	
	@PostMapping
	public Deck createDeck(@RequestBody Deck deck) {
		logger.info("Received request to create deck: {}", deck);
		return deckService.save(deck);
	}
	
	@PutMapping("/{id}")
	public Deck updateDeckName(@PathVariable("id") Long id, @RequestBody Deck updatedDeck) {
		logger.info("Received request to update deck: {}", updatedDeck);
		return deckService.updateDeckName(id, updatedDeck.getDeckName());
	}
	
	@DeleteMapping("/{id}")
	public void deleteDeck(@PathVariable("id") Long id) {
		logger.info("Received request to delete deck with id: {}", id);
		deckService.deleteDeck(id);
	}
}
