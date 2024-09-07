package com.example.iruka_backend.responsedto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class WordListResponse {

  private List<WordInfo> wordInfo;

}
