package com.example.iruka_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class WordUpdateEntity {

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
