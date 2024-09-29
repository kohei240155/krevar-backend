package com.example.iruka_backend.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserEntity {

  /** ユーザーID */
  private Long id;

  /** メールアドレス */
  private String email;

  /** ロール */
  private String role;

  /** GoogleID */
  private String googleId;

  /** 名前 */
  private String name;

  /** ネイティブ言語ID */
  private int defaultNativeLanguageId;

  /** 学習言語ID */
  private int defaultLearningLanguageId;

  /** 画像生成残数 */
  private int imageGenerationRemaining;

  /** 画像生成リセット日 */
  private LocalDate imageGenerationResetDate;

  /** サブスクリプションステータスID */
  private int subscriptionStatusId;

  /** サブスクリプションID */
  private String subscriptionId;

  /** ハイライト色 */
  private String highlightColor;

  /** 作成日時 */
  private LocalDateTime createdAt;

  /** 更新日時 */
  private LocalDateTime updatedAt;

  /** 削除フラグ */
  private int deleted;
}
