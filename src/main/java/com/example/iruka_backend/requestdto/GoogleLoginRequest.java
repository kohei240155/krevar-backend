package com.example.iruka_backend.requestdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GoogleLoginRequest {

    /** メールアドレス */
    private String email;

    /** 名前 */
    private String name;

    /** GoogleID */
    private String googleId;
}
