package com.example.krevar_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class WordCreateEntity {

    /** ユーザーID */
    private Long userId;

    /** 単語 */
    private String originalText;

    /** 翻訳 */
    private String translatedText;

    /** ニュアンス */
    private String nuanceText;

    /** 画像URL */
    private String imageUrl;

    /** デッキID */
    private Long deckId;
}
