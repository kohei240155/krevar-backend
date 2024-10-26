package com.example.krevar_backend.responsedto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeckInfo {

    /** デッキID */
    private Long id;

    /** デッキ名 */
    private String deckName;

    /** ネイティブ言語ID */
    private Long nativeLanguageId;

    /** 学習言語ID */
    private Long learningLanguageId;

    /** 進捗 */
    private int progress;
}
