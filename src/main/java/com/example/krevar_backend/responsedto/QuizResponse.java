package com.example.krevar_backend.responsedto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizResponse {
    /** クイズ情報 */
    private QuizInfo quizData;

    /** クイズの残数 */
    private int leftQuizCount;

    /** デッキ名 */
    private String deckName;
}
