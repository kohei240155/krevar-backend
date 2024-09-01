package com.example.iruka_backend.responsedto;

import java.util.List;
import com.example.iruka_backend.entity.WordEntity;

public class WordListResponse {
  private List<WordEntity> words;
  private long totalCount;

  public WordListResponse(List<WordEntity> words, long totalCount) {
    this.words = words;
    this.totalCount = totalCount;
  }

  public List<WordEntity> getWords() {
    return words;
  }

  public void setWords(List<WordEntity> words) {
    this.words = words;
  }

  public long getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(long totalCount) {
    this.totalCount = totalCount;
  }
}
