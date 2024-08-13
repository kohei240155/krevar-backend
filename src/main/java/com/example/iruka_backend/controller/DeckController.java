package com.example.iruka_backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.iruka_backend.entity.DeckEntity;
import com.example.iruka_backend.service.DeckService;
import com.example.iruka_backend.responsedto.DeckListResponse; // Added import

@RestController
@RequestMapping("/api/decks")
@CrossOrigin(origins = "http://localhost:3000")
public class DeckController {

	private static final Logger logger = LoggerFactory.getLogger(DeckController.class);

	@Autowired
	private DeckService deckService;

	@GetMapping
	public DeckListResponse getAllDecks(@RequestParam(name = "page", defaultValue = "0") int page,
									@RequestParam(name = "size", defaultValue = "10") int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<DeckEntity> decks = deckService.getDecks(pageable);
		long count = deckService.countActiveDecks(); // Added: count active decks
		logger.info("Total active decks: {}", count); // Log the count
		return new DeckListResponse(decks.getContent(), count); // Return DeckResponse
	}

	@PostMapping
	public DeckEntity createDeck(@RequestBody DeckEntity deck) {
		logger.info("Received request to create deck: {}", deck);
		return deckService.save(deck);
	}

	@PutMapping("/{id}")
	public DeckEntity updateDeckName(@PathVariable("id") Long id, @RequestBody DeckEntity updatedDeck) {
		logger.info("Received request to update deck: {}", updatedDeck);
		return deckService.updateDeckName(id, updatedDeck.getDeckName());
	}

	@DeleteMapping("/{id}")
	public void deleteDeck(@PathVariable("id") Long id) {
		logger.info("Received request to delete deck with id: {}", id);
		deckService.softDeleteDeck(id); // 物理削除から論理削除に変更
	}
}