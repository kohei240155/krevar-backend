package com.example.iruka_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.example.iruka_backend.entity.WordEntity;

@Repository
public interface WordRepository extends JpaRepository<WordEntity, Long> {
    List<WordEntity> findWordsByDeckId(Long deckId);
    List<WordEntity> findByDeckId(Long deckId);
	Page<WordEntity> findAllByDeletedAtIsNull(Pageable pageable); // ページネーション対応
	long countByDeletedAtIsNull(); // 有効なWordのカウント
}