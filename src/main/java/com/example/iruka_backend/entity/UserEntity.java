package com.example.iruka_backend.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class UserEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String role;

  @Column(name = "google_id")
  private String googleId;

  @Column(name = "created_at", nullable = false, updatable = false)
  private Timestamp createdAt;

  @Column(name = "updated_at", nullable = false)
  private Timestamp updatedAt;

  @Column(name = "deleted")
  private int deleted;

  @Column(name = "name")
  private String name;

  @Column(name = "default_native_language_id", nullable = false)
  private int defaultNativeLanguageId;

  @Column(name = "default_learning_language_id", nullable = false)
  private int defaultLearningLanguageId;

  @Column(name = "image_generation_remaining", nullable = false)
  private int imageGenerationRemaining;

  @Column(name = "image_generation_reset_date")
  private LocalDate imageGenerationResetDate;

  @Column(name = "subscription_status_id", nullable = false)
  private int subscriptionStatusId;

  @Column(name = "highlight_color", nullable = false)
  private String highlightColor = "#FFFF00";

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

  public UserEntity() {}

  public UserEntity(String email, String role, String googleId, int defaultNativeLanguageId,
      int defaultLearningLanguageId, int imageGenerationRemaining, int subscriptionStatusId) {
    this.email = email;
    this.role = role;
    this.googleId = googleId;
    this.defaultNativeLanguageId = defaultNativeLanguageId;
    this.defaultLearningLanguageId = defaultLearningLanguageId;
    this.imageGenerationRemaining = imageGenerationRemaining;
    this.subscriptionStatusId = subscriptionStatusId;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getGoogleId() {
    return googleId;
  }

  public void setGoogleId(String googleId) {
    this.googleId = googleId;
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

  public void softDelete() {
    deleted = 1;
  }

  public int getDeleted() {
    return deleted;
  }

  public void setDeleted(int deleted) {
    this.deleted = deleted;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getDefaultNativeLanguageId() {
    return defaultNativeLanguageId;
  }

  public void setDefaultNativeLanguageId(int defaultNativeLanguageId) {
    this.defaultNativeLanguageId = defaultNativeLanguageId;
  }

  public int getDefaultLearningLanguageId() {
    return defaultLearningLanguageId;
  }

  public void setDefaultLearningLanguageId(int defaultLearningLanguageId) {
    this.defaultLearningLanguageId = defaultLearningLanguageId;
  }

  public int getImageGenerationRemaining() {
    return imageGenerationRemaining;
  }

  public void setImageGenerationRemaining(int imageGenerationRemaining) {
    this.imageGenerationRemaining = imageGenerationRemaining;
  }

  public LocalDate getImageGenerationResetDate() {
    return imageGenerationResetDate;
  }

  public void setImageGenerationResetDate(LocalDate imageGenerationResetDate) {
    this.imageGenerationResetDate = imageGenerationResetDate;
  }

  public int getSubscriptionStatusId() {
    return subscriptionStatusId;
  }

  public void setSubscriptionStatusId(int subscriptionStatusId) {
    this.subscriptionStatusId = subscriptionStatusId;
  }

  public String getHighlightColor() {
    return highlightColor;
  }

  public void setHighlightColor(String highlightColor) {
    this.highlightColor = highlightColor;
  }
}
