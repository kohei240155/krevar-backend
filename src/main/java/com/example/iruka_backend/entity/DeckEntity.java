package com.example.iruka_backend.entity;

import java.sql.Timestamp; // Added this line to import java.sql.Timestamp
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
@Table(name = "decks")
public class DeckEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "deck_name", nullable = false)
  private String deckName;

  @Column(name = "user_id", nullable = false)
  private Long userId;

  @Column(name = "last_practiced_date", nullable = false)
  private LocalDate lastPracticedDate;

  @Column(name = "created_at", nullable = false, updatable = false)
  private Timestamp createdAt;

  @Column(name = "updated_at", nullable = false)
  private Timestamp updatedAt;

  @Column(name = "deleted_at")
  private Timestamp deletedAt;

  public DeckEntity() {}

  public DeckEntity(String deckName, long userId) {
    this.deckName = deckName;
    this.userId = userId;
  }

  @PrePersist
  protected void onCreate() {
    LocalDateTime now = LocalDateTime.now();
    createdAt = Timestamp.valueOf(now);
    updatedAt = Timestamp.valueOf(now);
    lastPracticedDate = now.toLocalDate();
  }

  @PreUpdate
  protected void onUpdate() {
    updatedAt = Timestamp.valueOf(LocalDateTime.now());
  }

  public void softDelete() {
    deletedAt = Timestamp.valueOf(LocalDateTime.now());
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

  public LocalDate getLastPracticedDate() {
    return lastPracticedDate;
  }

  public void setLastPracticedDate(LocalDate lastPracticedDate) {
    this.lastPracticedDate = lastPracticedDate;
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

  public Timestamp getDeletedAt() {
    return deletedAt;
  }

  public void setDeletedAt(Timestamp deletedAt) {
    this.deletedAt = deletedAt;
  }

  @Override
  public String toString() {
    return "Deck{" + "id=" + id + ", deckName='" + deckName + '\'' + ", userId=" + userId
        + ", lastPracticedDate=" + lastPracticedDate + ", createdAt=" + createdAt + ", updatedAt="
        + updatedAt + ", deletedAt=" + deletedAt + '}';
  }
}
