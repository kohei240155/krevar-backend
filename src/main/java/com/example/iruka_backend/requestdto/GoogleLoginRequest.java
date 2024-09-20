package com.example.iruka_backend.requestdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GoogleLoginRequest {

    /** メールアドレス */
    private String email;

    /** 名前 */
    private String name;

    /** GoogleID */
    private String googleId;

    /** ハイライトカラー */
    private String highlightColor;

    /** デフォルトの母語 */
    private int defaultNativeLanguageId;

    /** デフォルトの学習言語 */
    private int defaultLearningLanguageId;

    /** 画像生成リミット */
    private int imageGenerationRemaining;

    /** サブスクリプションステータス */
    private int subscriptionStatusId;
}
