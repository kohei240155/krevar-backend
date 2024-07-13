package com.example.iruka_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.iruka_backend.entity.Deck;

@Repository
public interface DeckRepository extends JpaRepository<Deck, Long> {
}
