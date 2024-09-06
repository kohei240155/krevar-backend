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
}
