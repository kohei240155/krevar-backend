package com.example.krevar_backend.repository;

import com.example.krevar_backend.entity.SubscriptionEntity;

public interface SubscriptionRepository {

    /**
     * サブスクリプションを取得
     *
     * @param plan プラン
     * @return サブスクリプション
     */
    SubscriptionEntity getSubscription(String plan);
}
