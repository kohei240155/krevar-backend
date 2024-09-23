package com.example.iruka_backend.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSettingsUpdateEntity {

    /** ネイティブ言語ID */
    private int nativeLanguageId;

    /** 学習言語ID */
    private int learningLanguageId;

    /** ハイライト色 */
    private String highlightColor;
}
