package com.example.iruka_backend.service;

import com.example.iruka_backend.responsedto.QuizResponse;

public interface QuizService {

    /**
     * ノーマルクイズ取得
     *
     * @param deckId
     * @return
     */
    public QuizResponse getNormalQuiz(Long deckId);

    /**
     * ノーマルクイズの残数を取得
     *
     * @param deckId
     * @return
     */
    public int getLeftNormalQuizCount(Long deckId);

    /**
     * ユーザーIDをチェックする
     *
     * @param userId
     */
    public void verifyUser(Long userId);

}
