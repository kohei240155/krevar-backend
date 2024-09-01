package com.example.iruka_backend.responsedto;

public class DeckUpdatedResponse {
  private Long id;
  private String deckName;
  private String message;

  public DeckUpdatedResponse(Long id, String deckName, String message) {
    this.id = id;
    this.deckName = deckName;
    this.message = message;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDeckName() {
    return deckName;
  }

  public void setDeckName(String deckName) {
    this.deckName = deckName;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
