package com.example.krevar_backend.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class WordEntity {

  /** 単語ID */
  private Long id;

  /** 元のテキスト */
  private String originalText;

  /** 翻訳されたテキスト */
  private String translatedText;

  /** ニュアンスのテキスト */
  private String nuanceText;

  /** 画像URL */
  private String imageUrl;

  /** デッキID */
  private Long deckId;

  /** レビュー間隔ID */
  private Long reviewIntervalId;

  /** 次の練習日 */
  private LocalDate nextPracticeDate;

  /** 正解数 */
  private Long correctCount;

  /** 不正解数 */
  private Long incorrectCount;

  /** エクストラモードで正解か */
  private Long isExtraModeCorrect;

  /** 作成日時 */
  private LocalDateTime createdAt;

  /** 更新日時 */
  private LocalDateTime updatedAt;

  /** 削除フラグ */
  private int deleted;
}
