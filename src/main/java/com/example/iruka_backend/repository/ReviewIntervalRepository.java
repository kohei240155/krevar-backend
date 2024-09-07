package com.example.iruka_backend.repository;

public interface ReviewIntervalRepository {

    /**
     * レビュー間隔を取得する
     *
     * @param reviewIntervalId
     * @return レビュー間隔
     */
    int getIntervalDay(int reviewIntervalId);
}
