package com.example.krevar_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LanguageEntity {

    /** 言語ID */
    private Long languageId;

    /** 言語コード */
    private String languageCode;

    /** 言語名 */
    private String languageName;

}
