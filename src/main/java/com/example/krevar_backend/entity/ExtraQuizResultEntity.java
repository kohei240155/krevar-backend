package com.example.krevar_backend.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ExtraQuizResultEntity {

    /** 単語ID */
    private Long id;

    /** エクストラモードの正誤フラグ */
    private int isExtraModeCorrect;

    /** 更新日時 */
    private LocalDateTime updatedAt;
}
