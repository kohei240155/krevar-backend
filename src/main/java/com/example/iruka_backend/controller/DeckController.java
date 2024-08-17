package com.example.iruka_backend.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
import com.example.iruka_backend.responsedto.DeckCreatedResponse;
import com.example.iruka_backend.responsedto.DeckListResponse;
import com.example.iruka_backend.responsedto.DeckProgressResponse;
import com.example.iruka_backend.responsedto.DeckUpdatedResponse;
import com.example.iruka_backend.service.DeckService;
import com.example.iruka_backend.service.QuizService;

@RestController
@RequestMapping("/api/decks")
@CrossOrigin(origins = "http://localhost:3000")
public class DeckController {

	private static final Logger logger = LoggerFactory.getLogger(DeckController.class);

	@Autowired
	private DeckService deckService;

	@Autowired
	private QuizService quizService;

	@GetMapping
	public DeckListResponse getAllDecks(@RequestParam(name = "page", defaultValue = "0") int page,
									 @RequestParam(name = "size", defaultValue = "10") int size) {
		Pageable pageable = PageRequest.of(page, size);
		List<DeckEntity> allDecks = deckService.getAllDecksSortedByDueTodayAndIncorrectWords(); // 修正: next_practice_dateが当日で、is_normal_mode_correctが0のWordが多い順に並べ替え
		long count = deckService.countActiveDecks(); // Added: count active decks
		logger.info("Total active decks: {}", count); // Log the count

		// ページネーションを適用
		int start = (int) pageable.getOffset();
		int end = Math.min((start + pageable.getPageSize()), allDecks.size());
		Page<DeckEntity> decksPage = new PageImpl<>(allDecks.subList(start, end), pageable, allDecks.size());

		// 進捗情報を取得
		List<DeckProgressResponse> deckProgressResponses = decksPage.getContent().stream()
			.map(deck -> {
				long totalQuestions = quizService.getTodayQuestionCountByDeckId(deck.getId());
				return new DeckProgressResponse(deck, totalQuestions);
			})
			.collect(Collectors.toList());

		return new DeckListResponse(deckProgressResponses, count); // Return DeckResponse with progress
	}

	@PostMapping
	public DeckCreatedResponse createDeck(@RequestBody DeckEntity deck) {
		logger.info("Received request to create deck: {}", deck);
		try {
			 DeckEntity createdDeck = deckService.save(deck);
			return new DeckCreatedResponse(createdDeck.getId(), "Deck created successfully");
		} catch (Exception e) {
			logger.error("Error creating deck: {}", e.getMessage());
			return new DeckCreatedResponse(null, "Error creating deck");
		}
	}

	@PutMapping("/{id}")
	public DeckUpdatedResponse updateDeckName(@PathVariable("id") Long id, @RequestBody DeckEntity updatedDeck) {
		logger.info("Received request to update deck: {}", updatedDeck);
		try {
			DeckEntity updatedEntity = deckService.updateDeckName(id, updatedDeck.getDeckName());
			return new DeckUpdatedResponse(updatedEntity.getId(), updatedEntity.getDeckName(), "Deck updated successfully");
		} catch (Exception e) {
			logger.error("Error updating deck: {}", e.getMessage());
			return new DeckUpdatedResponse(null, null, "Error updating deck");
		}
	}

	@DeleteMapping("/{id}")
	public void deleteDeck(@PathVariable("id") Long id) {
		logger.info("Received request to delete deck with id: {}", id);
		deckService.softDeleteDeck(id); // 物理削除から論理削除に変
	}
}