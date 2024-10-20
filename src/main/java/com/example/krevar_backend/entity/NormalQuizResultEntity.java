package com.example.krevar_backend.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NormalQuizResultEntity {

    /** 単語ID */
    private Long id;

    /** レビュー間隔ID */
    private int reviewIntervalId;

    /** 次の練習日 */
    private LocalDate nextPracticeDate;

    /** 正解数 */
    private int correctCount;

    /** 不正解数 */
    private int incorrectCount;

    /** 更新日時 */
    private LocalDateTime updatedAt;
}
