package com.example.iruka_backend.responsedto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeckInfo {

    /** デッキID */
    private Long id;

    /** デッキ名 */
    private String deckName;

    /** 進捗 */
    private int progress;
}
