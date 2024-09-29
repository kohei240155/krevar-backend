package com.example.iruka_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SubscriptionEntity {

    /** ID */
    private Long id;

    /** プラン */
    private String plan;

    /** 画像最大生成数 */
    private int maxImageGeneration;

}
