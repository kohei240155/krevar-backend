package com.example.iruka_backend.responsedto;

import java.util.List;

public class DeckListResponse {
  private List<DeckProgressResponse> decks;
  private long totalDecks;

  public DeckListResponse(List<DeckProgressResponse> decks, long totalDecks) {
    this.decks = decks;
    this.totalDecks = totalDecks;
  }

  public List<DeckProgressResponse> getDecks() {
    return decks;
  }

  public void setDecks(List<DeckProgressResponse> decks) {
    this.decks = decks;
  }

  public long getTotalDecks() {
    return totalDecks;
  }

  public void setTotalDecks(long totalDecks) {
    this.totalDecks = totalDecks;
  }
}
