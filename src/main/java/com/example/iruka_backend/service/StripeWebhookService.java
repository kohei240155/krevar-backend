package com.example.iruka_backend.service;

public interface StripeWebhookService {

    /**
     * 支払い情報をデータベースに保存する
     *
     * @param userId
     * @param priceId
     * @param subscriptionId
     */
    public void savePaymentInfo(Long userId, String plan, String subscriptionId);

}
