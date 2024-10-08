package com.example.iruka_backend.controller;

import com.example.iruka_backend.service.StripeWebhookService;
import com.stripe.model.Event;
import com.stripe.model.Subscription;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class StripeWebhookController {

    private static final String STRIPE_ENDPOINT_SECRET =
            "whsec_55dd322250e8b885f05682f0cc39e8fe6018a77e7c19518ef245718d92f999e1"; // Webhookシークレット

    @Autowired
    private StripeWebhookService stripeWebhookService;

    private static final Logger logger = LoggerFactory.getLogger(StripeWebhookController.class);

    /**
     * StripeのWebhookを受け取る
     *
     * @param payload
     * @param sigHeader
     */
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
                    Long userId = Long.parseLong(session.getMetadata().get("user_id"));
                    logger.info("Payment was successful for user: " + userId);

                    // サブスクリプションIDを取得
                    String subscriptionId = session.getSubscription();
                    logger.info("Subscription ID: " + subscriptionId);

                    // サブスクリプション情報を取得
                    Subscription subscription = Subscription.retrieve(subscriptionId);
                    String priceId = subscription.getItems().getData().get(0).getPrice().getId();
                    logger.info("Price ID (Plan): " + priceId);

                    // プランの価格IDに基づき、支払ったプランを判別
                    String plan;
                    switch (priceId) {
                        case "price_1Q4CZDP4n7axDIegFeOCv21I":
                            plan = "light";
                            break;
                        case "price_1Q4CcIP4n7axDIegHrD4XjNb":
                            plan = "basic";
                            break;
                        case "price_1Q4CcqP4n7axDIeg5ihiTUIh":
                            plan = "pro";
                            break;
                        default:
                            plan = "unknown";
                    }

                    // 支払い情報をデータベースに保存
                    stripeWebhookService.savePaymentInfo(userId, plan, subscriptionId);

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
