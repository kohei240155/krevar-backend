package com.example.iruka_backend.requestdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeckUpdateRequest {

  /** ユーザーID */
  private Long userId;

  /** デッキ名 */
  private String deckName;

  /** デッキの母語 */
  private String nativeLanguageId;

  /** デッキの学習言語 */
  private String learningLanguageId;
}
