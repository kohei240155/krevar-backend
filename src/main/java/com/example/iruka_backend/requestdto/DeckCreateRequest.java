package com.example.iruka_backend.requestdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeckCreateRequest {

    /** ユーザーID */
    private int userId;

    /** デッキ名 */
    private String deckName;

}