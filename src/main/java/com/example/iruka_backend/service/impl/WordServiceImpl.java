package com.example.iruka_backend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.iruka_backend.entity.Word;
import com.example.iruka_backend.repository.WordRepository;
import com.example.iruka_backend.service.WordService;

@Service
public class WordServiceImpl implements WordService {

	@Autowired
	private WordRepository wordRepository;
	
	@Override
	public List<Word> getWordsByDeckId(Long deckId) {
		return wordRepository.findByDeckId(deckId);
	}

	@Override
	public Word save(Word word) {
		return wordRepository.save(word);
	}
	
}
