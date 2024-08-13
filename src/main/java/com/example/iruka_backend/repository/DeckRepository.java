package com.example.iruka_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.example.iruka_backend.entity.DeckEntity;

import java.util.List;

@Repository
public interface DeckRepository extends JpaRepository<DeckEntity, Long> {
	// 追加: ソフトデリートされたデッキを除外するクエリ
	List<DeckEntity> findByDeletedAtIsNull();
	Page<DeckEntity> findAllByDeletedAtIsNull(Pageable pageable);
	long countByDeletedAtIsNull(); // 追加: 有効なデッキのカウントクエリ
}