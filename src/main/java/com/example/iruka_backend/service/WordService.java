package com.example.iruka_backend.service;

import java.util.List;
import java.util.Optional;

import com.example.iruka_backend.entity.Word;

public interface WordService {
	List<Word> getWordsByDeckId(Long deckId);
	Word save(Word word);
	Optional<Word> getWordById(Long wordId);
	Word update(Word word);
}