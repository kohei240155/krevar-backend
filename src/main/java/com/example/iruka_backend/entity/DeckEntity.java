package com.example.iruka_backend.entity;

import java.time.LocalDateTime;
import java.sql.Timestamp; // Added this line to import java.sql.Timestamp

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import com.example.iruka_backend.common.entity.BaseEntity;

@Entity
@Table(name = "decks")
public class DeckEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "deck_name", nullable = false)
	private String deckName;

	@Column(name = "user_id", nullable = false)
	private Long userId;

	@Column(name = "last_practiced_date", nullable = false)
	private LocalDateTime lastPracticedDate;

	public DeckEntity() {}

	public DeckEntity(String deckName, long userId) {
		this.deckName = deckName;
		this.userId = userId;
	}

	@PrePersist
	protected void onCreate() {
		LocalDateTime now = LocalDateTime.now();
		setCreatedAt(Timestamp.valueOf(now));
		setUpdatedAt(Timestamp.valueOf(now));
		lastPracticedDate = now;
	}

	@PreUpdate
	protected void onUpdate() {
		setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public LocalDateTime getLastPracticedDate() {
		return lastPracticedDate;
	}

	public void setLastPracticedDate(LocalDateTime lastPracticedDate) {
		this.lastPracticedDate = lastPracticedDate;
	}

	@Override
	public String toString() {
		return "Deck{" +
				"id=" + id +
				", deckName='" + deckName + '\'' +
				", userId=" + userId +
				", lastPracticedDate=" + lastPracticedDate +
				", createdAt=" + getCreatedAt() +
				", updatedAt=" + getUpdatedAt() +
				", deletedAt=" + getDeletedAt() +
				'}';
	}
}