package com.example.iruka_backend.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "words")
public class Word {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "original_text", nullable = false)
	private String originalText;
	
	@Column(name = "translated_text")
	private String translatedText;
	
	@Column(name = "original_image_url")
	private String originalImageUrl;
	
	@Column(name = "mastery_status_id", nullable = false)
	private Long masteryStatusId;
	
	@Column(name = "last_practiced_date", nullable = false)
	private LocalDateTime lastPracticedDate;
	
	@Column(name = "next_practice_date", nullable = false)
	private LocalDateTime nextPracticeDate;
	
	@Column(name = "deck_id", nullable = false)
	private long deckId;
	
	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;
	
	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getOriginalImageUrl() {
		return originalImageUrl;
	}

	public void setOriginalImageUrl(String originalImageUrl) {
		this.originalImageUrl = originalImageUrl;
	}

	public Long getMasteryStatusId() {
		return masteryStatusId;
	}

	public void setMasteryStatusId(Long masteryStatusId) {
		this.masteryStatusId = masteryStatusId;
	}

	public LocalDateTime getLastPracticedDate() {
		return lastPracticedDate;
	}

	public void setLastPracticedDate(LocalDateTime lastPracticedDate) {
		this.lastPracticedDate = lastPracticedDate;
	}

	public LocalDateTime getNextPracticeDate() {
		return nextPracticeDate;
	}

	public void setNextPracticeDate(LocalDateTime nextPracticeDate) {
		this.nextPracticeDate = nextPracticeDate;
	}

	public long getDeckId() {
		return deckId;
	}

	public void setDeckId(long deckId) {
		this.deckId = deckId;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
}
