package com.example.iruka_backend.requestdto;

public class ImageUploadRequest {
  private String imagePath;
  private Long wordId;

  // Getters and Setters
  public String getImagePath() {
    return imagePath;
  }

  public void setImagePath(String imagePath) {
    this.imagePath = imagePath;
  }

  public Long getWordId() {
    return wordId;
  }

  public void setWordId(Long wordId) {
    this.wordId = wordId;
  }
}
