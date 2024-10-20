package com.example.krevar_backend.service;

import com.example.krevar_backend.requestdto.ExtraQuizResultUpdateRequest;
import com.example.krevar_backend.requestdto.NormalQuizResultUpdateRequest;
import com.example.krevar_backend.responsedto.QuizResponse;

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
     * ノーマルクイズ更新
     *
     * @param request
     */
    public void updateNormalQuiz(NormalQuizResultUpdateRequest request);

    /**
     * エクストラクイズ取得
     *
     * @param deckId
     * @return
     */
    public QuizResponse getExtraQuiz(Long deckId);

    /**
     * エクストラクイズ更新
     *
     * @param request
     */
    public void updateExtraQuiz(ExtraQuizResultUpdateRequest request);

    /**
     * エクストラクイズの残数を取得
     *
     * @param deckId
     * @return
     */
    public int getLeftExtraQuizCount(Long deckId);

    /**
     * エクストラクイズリセット
     *
     * @param deckId
     */
    public void resetExtraQuiz(Long deckId);
}
