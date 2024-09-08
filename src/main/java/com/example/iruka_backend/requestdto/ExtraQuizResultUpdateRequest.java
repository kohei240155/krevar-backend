package com.example.iruka_backend.requestdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExtraQuizResultUpdateRequest {

    /** ユーザーID */
    private Long userId;

    /** 単語ID */
    private Long wordId;

    /** エクストラモードの正誤フラグ */
    private int isExtraModeCorrect;

}
