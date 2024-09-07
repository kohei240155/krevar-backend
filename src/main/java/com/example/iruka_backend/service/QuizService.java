package com.example.iruka_backend.service;

import com.example.iruka_backend.responsedto.WordResponse;

public interface QuizService {

    /**
     * ノーマルクイズ取得
     *
     * @param deckId
     * @return
     */
    public WordResponse getNormalQuiz(Long deckId);

    /**
     * ユーザーIDをチェックする
     *
     * @param userId
     */
    public void verifyUser(Long userId);

}
