package com.example.iruka_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.iruka_backend.entity.Word;

@Repository
public interface QuizRepository extends JpaRepository<Word, Long> {
	List<Word> findByDeckId(Long deckId);
}
