package com.example.iruka_backend.controller;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.model.Subscription;
import com.stripe.param.checkout.SessionCreateParams;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class StripeCheckoutController {

    private static final Logger log = LoggerFactory.getLogger(StripeCheckoutController.class);

    @Value("${stripe.api.secret-key}")
    private String stripeSecretKey;

    @PostMapping("/create-checkout-session")
    public Map<String, String> createCheckoutSession(@RequestBody Map<String, Object> payload)
            throws StripeException {
        Stripe.apiKey = stripeSecretKey;

        String userId = (String) payload.get("userId"); // フロントエンドから受け取ったユーザーID
        String plan = (String) payload.get("plan"); // フロントエンドから受け取ったプラン

        // プランに応じてStripeの価格IDを設定
        String priceId;
        switch (plan) {
            case "trial":
                // トライアルプランの価格ID
                priceId = "price_1Q4CZDP4n7axDIegFeOCv21I";
                break;
            case "basic":
                // ベーシックプランの価格ID
                priceId = "price_1Q4CcIP4n7axDIegHrD4XjNb";
                break;
            case "pro":
                // プロプランの価格ID
                priceId = "price_1Q4CcqP4n7axDIeg5ihiTUIh";
                break;
            default:
                throw new IllegalArgumentException("Invalid plan");

        }

        // Stripeのセッションを作成
        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
                .setSuccessUrl("http://localhost:3000/deck") // 支払い成功後のリダイレクトURL
                .setCancelUrl("http://localhost:3000/deck") // 支払いキャンセル後のリダイレクトURL
                .addLineItem(SessionCreateParams.LineItem.builder().setPrice(priceId) // 選択されたプランに対応する価格IDを使用
                        .setQuantity(1L).build())
                .putMetadata("user_id", userId) // メタデータにユーザーIDを設定
                .build();

        Session session = Session.create(params);

        // CheckoutページへのURLをフロントエンドに返す
        Map<String, String> responseData = new HashMap<>();
        responseData.put("url", session.getUrl());

        return responseData;
    }

    // サブスクリプションのキャンセルエンドポイント
    @PostMapping("/cancel-subscription")
    public Map<String, Object> cancelSubscription(@RequestBody Map<String, Object> payload)
            throws StripeException {
        Stripe.apiKey = stripeSecretKey;

        // フロントエンドから送信されたサブスクリプションIDを取得
        String subscriptionId = (String) payload.get("subscriptionId");

        log.info("subscriptionId: {}", subscriptionId);

        // サブスクリプションをキャンセル
        Subscription subscription = Subscription.retrieve(subscriptionId);
        Subscription canceledSubscription = subscription.cancel();

        // キャンセル後のサブスクリプション情報を返す
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("status", canceledSubscription.getStatus());
        responseData.put("id", canceledSubscription.getId());

        return responseData;
    }
}
