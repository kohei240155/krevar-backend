package com.example.krevar_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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

    /** 更新日時 */
    private LocalDateTime updatedAt;
}
