package com.example.krevar_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserLoginEntity {

    /** メールアドレス */
    private String email;

    /** ロール */
    private String role;

    /** GoogleID */
    private String googleId;

    /** 名前 */
    private String name;

    /** 画像生成残数 */
    private int imageGenerationRemaining;

    /** サブスクリプションステータスID */
    private Long subscriptionStatusId;

}
