package com.example.iruka_backend.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import org.slf4j.LoggerFactory;

@RestController
public class StripeWebhookController {

    private static final String STRIPE_ENDPOINT_SECRET =
            "whsec_55dd322250e8b885f05682f0cc39e8fe6018a77e7c19518ef245718d92f999e1"; // Webhookシークレット

    private static final org.slf4j.Logger logger =
            LoggerFactory.getLogger(StripeWebhookController.class);

    @PostMapping("/webhook")
    public void handleStripeWebhook(@RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sigHeader) {

        logger.info("------------- StripeWebhookController 開始 -------------");

        try {
            // Webhookの署名を検証
            Event event = Webhook.constructEvent(payload, sigHeader, STRIPE_ENDPOINT_SECRET);

            // イベントタイプを確認
            if ("payment_intent.succeeded".equals(event.getType())) {
                logger.info("Payment was successful!");
                // ロジックをここに追加
            }

        } catch (Exception e) {
            // エラーハンドリング
            logger.error("Error processing webhook: " + e.getMessage());
            e.printStackTrace();
        }

        logger.info("------------- StripeWebhookController 終了 -------------");
    }
}
