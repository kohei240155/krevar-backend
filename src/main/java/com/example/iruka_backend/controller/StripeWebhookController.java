package com.example.iruka_backend.controller;

import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
// @RequestMapping("/api")
// @CrossOrigin(origins = "http://localhost:3000")
public class StripeWebhookController {

    private static final String STRIPE_ENDPOINT_SECRET =
            "whsec_55dd322250e8b885f05682f0cc39e8fe6018a77e7c19518ef245718d92f999e1"; // Webhookシークレット

    private static final Logger logger = LoggerFactory.getLogger(StripeWebhookController.class);

    @PostMapping("/webhook")
    public void handleStripeWebhook(@RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sigHeader) {

        logger.info("------------- StripeWebhookController 開始 -------------");

        try {
            // Webhookの署名を検証
            Event event = Webhook.constructEvent(payload, sigHeader, STRIPE_ENDPOINT_SECRET);

            // イベントタイプを確認
            if ("checkout.session.completed".equals(event.getType())) {
                // Checkoutセッションの完了を処理
                Session session =
                        (Session) event.getDataObjectDeserializer().getObject().orElse(null);
                if (session != null) {
                    // メタデータからユーザーIDを取得
                    String userId = session.getMetadata().get("user_id");
                    logger.info("Payment was successful for user: " + userId);

                    // ユーザーIDを使って、支払い情報をデータベースに保存するなどの処理を行う
                }
            }

        } catch (Exception e) {
            // エラーハンドリング
            logger.error("Error processing webhook: " + e.getMessage());
            e.printStackTrace();
        }

        logger.info("------------- StripeWebhookController 終了 -------------");
    }
}
