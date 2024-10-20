package com.example.krevar_backend.entity;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserSubscriptionEntity {

    /** ユーザーID */
    private Long id;

    /** サブスクリプションステータスID */
    private Long subscriptionStatusId;

    /** 画像生成残数 */
    private int imageGenerationRemaining;

    /** 画像生成リセット日 */
    private LocalDate imageGenerationResetDate;

    /** サブスクリプションID */
    private String subscriptionId;
}
