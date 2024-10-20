package com.example.krevar_backend.responsedto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DeckListResponse {

  private List<DeckInfo> deckInfo;

}
