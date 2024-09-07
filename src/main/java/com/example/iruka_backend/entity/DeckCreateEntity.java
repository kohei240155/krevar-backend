package com.example.iruka_backend.entity;

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

}
