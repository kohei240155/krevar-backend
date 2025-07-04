package com.example.krevar_backend.requestdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeckCreateRequest {

    /** デッキ名 */
    private String deckName;

    /** デッキの母語 */
    private String nativeLanguageId;

    /** デッキの学習言語 */
    private String learningLanguageId;

}
