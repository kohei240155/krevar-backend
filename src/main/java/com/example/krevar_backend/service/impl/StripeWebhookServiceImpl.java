package com.example.krevar_backend.service.impl;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.krevar_backend.entity.SubscriptionEntity;
import com.example.krevar_backend.entity.UserEntity;
import com.example.krevar_backend.entity.UserSubscriptionEntity;
import com.example.krevar_backend.repository.SubscriptionRepository;
import com.example.krevar_backend.repository.UserRepository;
import com.example.krevar_backend.service.StripeWebhookService;

@Service
public class StripeWebhookServiceImpl implements StripeWebhookService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void savePaymentInfo(Long userId, String plan, String subscriptionId) {

        // ユーザーが存在するか確認
        UserEntity user = userRepository.findUserByUserId(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        // サブスクリプションを取得
        SubscriptionEntity subscription = subscriptionRepository.getSubscription(plan);

        UserSubscriptionEntity userSubscription =
                new UserSubscriptionEntity(userId, subscription.getId(),
                        subscription.getMaxImageGeneration(), LocalDate.now(), subscriptionId);

        // 支払い情報をデータベースに保存
        userRepository.saveUserSubscription(userSubscription);
    }

}
