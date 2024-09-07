package com.example.iruka_backend.responsedto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizResponse {

    /** 単語ID */
    private Long id;

    /** 単語 */
    private String originalText;

    /** 翻訳 */
    private String translatedText;

    /** ニュアンス */
    private String nuanceText;

    /** 画像URL */
    private String imageUrl;

    /** クイズの残数 */
    private int leftQuizCount;
}
