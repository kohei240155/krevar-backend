package com.example.iruka_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DeckUpdateEntity {

    /** ユーザーID */
    private Long userId;

    /** デッキ名 */
    private String deckName;
}
