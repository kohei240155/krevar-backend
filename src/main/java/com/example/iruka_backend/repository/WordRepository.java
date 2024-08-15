package com.example.iruka_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.iruka_backend.entity.WordEntity;

@Repository
public interface WordRepository extends JpaRepository<WordEntity, Long> {
    List<WordEntity> findWordsByDeckId(Long deckId);
    List<WordEntity> findByDeckId(Long deckId);
	Page<WordEntity> findAllByDeletedAtIsNull(Pageable pageable); // ページネーション対応
	long countByDeletedAtIsNull(); // 有効なWordのカウント

    @Query("SELECT COUNT(w) FROM WordEntity w WHERE w.deckId = :deckId AND w.isCorrect = false AND w.nextPracticeDate <= CURRENT_DATE")
    Long findCountByDeckIdAndIsCorrectFalseAndNextPracticeDate(@Param("deckId") Long deckId);

    Long countByDeckIdAndIsCorrectTrue(Long deckId); // メソッドの追加

    @Query("SELECT COUNT(w) FROM WordEntity w WHERE w.deckId = :deckId AND w.nextPracticeDate = CURRENT_DATE")
    Long findTodayQuestionCountByDeckId(@Param("deckId") Long deckId);
}