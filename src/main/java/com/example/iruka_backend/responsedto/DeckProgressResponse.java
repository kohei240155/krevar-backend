package com.example.iruka_backend.responsedto;

import com.example.iruka_backend.entity.DeckEntity;

public class DeckProgressResponse {
	private DeckEntity deck;
	private long totalQuestions;
	private long correctQuestions;

	public DeckProgressResponse(DeckEntity deck, long totalQuestions, long correctQuestions) {
		this.deck = deck;
		this.totalQuestions = totalQuestions;
		this.correctQuestions = correctQuestions;
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

	public long getCorrectQuestions() {
		return correctQuestions;
	}

	public void setCorrectQuestions(long correctQuestions) {
		this.correctQuestions = correctQuestions;
	}
}