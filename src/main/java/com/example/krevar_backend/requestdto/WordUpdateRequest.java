package com.example.krevar_backend.requestdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WordUpdateRequest {

  /** 単語ID */
  private Long wordId;

  /** ユーザーID */
  private Long userId;

  /** 単語 */
  private String originalText;

  /** 翻訳 */
  private String translatedText;

  /** ニュアンス */
  private String nuanceText;

  /** デッキID */
  private Long deckId;

}
