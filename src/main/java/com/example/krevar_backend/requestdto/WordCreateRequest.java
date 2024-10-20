package com.example.krevar_backend.requestdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WordCreateRequest {

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
