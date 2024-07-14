package com.example.iruka_backend.service;

import java.util.List;

import com.example.iruka_backend.entity.Word;

public interface WordService {
	List<Word> getWordsByDeckId(Long deckId);
}