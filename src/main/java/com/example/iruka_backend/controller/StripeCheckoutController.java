package com.example.iruka_backend.controller;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class StripeCheckoutController {

    @Value("${stripe.api.secret-key}")
    private String stripeSecretKey;

    @PostMapping("/create-checkout-session")
    public Map<String, String> createCheckoutSession(@RequestBody Map<String, Object> payload)
            throws StripeException {
        Stripe.apiKey = stripeSecretKey;

        String userId = (String) payload.get("userId"); // フロントエンドから受け取ったユーザーID

        // Stripeのセッションを作成
        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD) // 修正箇所
                .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
                .setSuccessUrl("https://example.com/success") // 支払い成功後のリダイレクトURL
                .setCancelUrl("https://example.com/cancel") // 支払いキャンセル後のリダイレクトURL
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setPrice("price_1Q2uJrP4n7axDIegizGOsO9Y") // Stripeダッシュボードで設定した価格ID
                        .setQuantity(1L).build())
                .putMetadata("user_id", userId) // メタデータにユーザーIDを設定
                .build();

        Session session = Session.create(params);

        // CheckoutページへのURLをフロントエンドに返す
        Map<String, String> responseData = new HashMap<>();
        responseData.put("url", session.getUrl());

        return responseData;
    }
}

