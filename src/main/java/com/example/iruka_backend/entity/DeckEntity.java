package com.example.iruka_backend.entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DeckEntity {

  /** デッキID */
  private Long id;

  /** デッキ名 */
  private String deckName;

  /** ユーザーID */
  private Long userId;

  /** 作成日時 */
  private LocalDateTime createdAt;

  /** 更新日時 */
  private LocalDateTime updatedAt;

  /** 削除フラグ */
  private int deleted;
}
