package com.example.krevar_backend.responsedto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class WordListResponse {

  /** 単語一覧 */
  private List<WordInfo> wordInfo;

  /** デッキ名 */
  private String deckName;

  /** 総単語数 */
  private int totalWordCount;
}
