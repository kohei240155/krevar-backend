package com.example.krevar_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DeckCreateEntity {

    /** ユーザーID */
    private Long userId;

    /** デッキ名 */
    private String deckName;

    /** デッキの母語 */
    private String nativeLanguageId;

    /** デッキの学習言語 */
    private String learningLanguageId;

}
