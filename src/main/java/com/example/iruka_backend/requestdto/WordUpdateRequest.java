package com.example.iruka_backend.requestdto;

public class WordUpdateRequest {

    private String originalText;

    private String translatedText;

    private String imageUrl;

    private Long deckId;

    private String nuanceText;

    // Getters and Setters
    public String getOriginalText() {
        return originalText;
    }

    public void setOriginalText(String originalText) {
        this.originalText = originalText;
    }

    public String getTranslatedText() {
        return translatedText;
    }

    public void setTranslatedText(String translatedText) {
        this.translatedText = translatedText;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getDeckId() {
        return deckId;
    }

    public void setDeckId(Long deckId) {
        this.deckId = deckId;
    }

    public String getNuanceText() {
        return nuanceText;
    }

    public void setNuanceText(String nuanceText) {
        this.nuanceText = nuanceText;
    }
}