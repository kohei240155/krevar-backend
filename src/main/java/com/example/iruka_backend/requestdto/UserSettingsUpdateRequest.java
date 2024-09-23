package com.example.iruka_backend.requestdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSettingsUpdateRequest {

    /** ネイティブ言語ID */
    private int nativeLanguageId;

    /** 学習言語ID */
    private int learningLanguageId;

    /** ハイライト色 */
    private String highlightColor;
}
