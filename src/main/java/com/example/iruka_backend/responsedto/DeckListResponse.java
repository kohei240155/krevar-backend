package com.example.iruka_backend.responsedto;

import java.util.List;
import com.example.iruka_backend.entity.DeckEntity;

public class DeckListResponse {
	private List<DeckEntity> decks;
	private long totalDecks;

	public DeckListResponse(List<DeckEntity> decks, long totalDecks) {
		this.decks = decks;
		this.totalDecks = totalDecks;
	}

	public List<DeckEntity> getDecks() {
		return decks;
	}

	public void setDecks(List<DeckEntity> decks) {
		this.decks = decks;
	}

	public long getTotalDecks() {
		return totalDecks;
	}

	public void setTotalDecks(long totalDecks) {
		this.totalDecks = totalDecks;
	}
}