package com.example.krevar_backend.requestdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NormalQuizResultUpdateRequest {

    /** ユーザーID */
    private Long userId;

    /** デッキID */
    private Long deckId;

    /** 単語ID */
    private Long wordId;

    /** クイズ結果 */
    private Boolean isCorrect;

}
