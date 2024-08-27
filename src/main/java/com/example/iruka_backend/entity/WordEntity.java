package com.example.iruka_backend.entity;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "words")
public class WordEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "original_text", nullable = false)
	private String originalText;

	@Column(name = "translated_text", nullable = false)
	private String translatedText;

	@Column(name = "nuance_text", nullable = false)
	private String nuanceText;

	@Column(name = "image_url")
	private String imageUrl;

	@Column(name = "review_interval_id", nullable = false)
	private Long reviewIntervalId;

	@Column(name = "next_practice_date", nullable = false)
	private LocalDate nextPracticeDate;

	@Column(name = "correct_count", nullable = false)
	private Long correctCount;

	@Column(name = "incorrect_count", nullable = false)
	private Long incorrectCount;

	@Column(name = "is_normal_mode_correct", nullable = false)
	private Boolean isNormalModeCorrect;

	@Column(name = "is_extra_mode_correct", nullable = false)
	private Boolean isExtraModeCorrect;

	@Column(name = "deck_id", nullable = false)
	private Long deckId;

	@Column(name = "created_at", nullable = false, updatable = false)
	private Timestamp createdAt;

	@Column(name = "updated_at", nullable = false)
	private Timestamp updatedAt;

	@PrePersist
	protected void onCreate() {
		LocalDateTime now = LocalDateTime.now();
		createdAt = Timestamp.valueOf(now);
		updatedAt = Timestamp.valueOf(now);
	}

	@PreUpdate
	protected void onUpdate() {
		updatedAt = Timestamp.valueOf(LocalDateTime.now());
	}

	@Column(name = "deleted_at")
	private Timestamp deletedAt;

	public void softDelete() {
		deletedAt = Timestamp.valueOf(LocalDateTime.now());
	}

	public Timestamp getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(Timestamp deletedAt) {
		this.deletedAt = deletedAt;
	}

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

	public String getNuanceText() {
		return nuanceText;
	}

	public void setNuanceText(String nuanceText) {
		this.nuanceText = nuanceText;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Long getReviewIntervalId() {
		return reviewIntervalId;
	}

	public void setReviewIntervalId(Long reviewIntervalId) {
		this.reviewIntervalId = reviewIntervalId;
	}

	public LocalDate getNextPracticeDate() {
		return nextPracticeDate;
	}

	public void setNextPracticeDate(LocalDate nextPracticeDate) {
		this.nextPracticeDate = nextPracticeDate;
	}

	public Long getCorrectCount() {
		return correctCount;
	}

	public void setCorrectCount(Long correctCount) {
		this.correctCount = correctCount;
	}

	public Long getIncorrectCount() {
		return incorrectCount;
	}

	public void setIncorrectCount(Long incorrectCount) {
		this.incorrectCount = incorrectCount;
	}

	public Boolean getIsNormalModeCorrect() {
		return isNormalModeCorrect;
	}

	public void setIsNormalModeCorrect(Boolean isNormalModeCorrect) {
		this.isNormalModeCorrect = isNormalModeCorrect;
	}

	public Boolean getIsExtraModeCorrect() {
		return isExtraModeCorrect;
	}

	public void setIsExtraModeCorrect(Boolean isExtraModeCorrect) {
		this.isExtraModeCorrect = isExtraModeCorrect;
	}

	public Long getDeckId() {
		return deckId;
	}

	public void setDeckId(Long deckId) {
		this.deckId = deckId;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}
}