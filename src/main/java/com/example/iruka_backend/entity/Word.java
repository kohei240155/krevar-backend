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
	
	@Column(name = "correct_count", nullable = false)
	private Long correct_count;

	@Column(name = "incorrect_count", nullable = false)
	private Long incorrect_count;
	
	@Column(name = "last_result", nullable = true)
	private String last_result;
	
	@Column(name = "deck_id", nullable = false)
	private Long deckId;
	
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
	
	public Long getCorrect_count() {
		return correct_count;
	}

	public void setCorrect_count(Long correct_count) {
		this.correct_count = correct_count;
	}

	public Long getIncorrect_count() {
		return incorrect_count;
	}

	public void setIncorrect_count(Long incorrect_count) {
		this.incorrect_count = incorrect_count;
	}

	public String getLast_result() {
		return last_result;
	}

	public void setLast_result(String last_result) {
		this.last_result = last_result;
	}

	public long getDeckId() {
		return deckId;
	}
	
	public void setDeckId(Long deckId) {
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
