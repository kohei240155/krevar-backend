package com.example.krevar_backend.entity;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
public class UserSettingsEntity {

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
}
