package com.example.iruka_backend.responsedto;

import com.example.iruka_backend.entity.DeckEntity;

public class DeckProgressResponse {
	private DeckEntity deck;
	private long totalQuestions;

	public DeckProgressResponse(DeckEntity deck, long totalQuestions) {
		this.deck = deck;
		this.totalQuestions = totalQuestions;
	}

	public DeckEntity getDeck() {
		return deck;
	}

	public void setDeck(DeckEntity deck) {
		this.deck = deck;
	}

	public long getTotalQuestions() {
		return totalQuestions;
	}

	public void setTotalQuestions(long totalQuestions) {
		this.totalQuestions = totalQuestions;
	}
}