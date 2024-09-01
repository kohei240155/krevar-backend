// package com.example.iruka_backend.repository.impl;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Repository;
// import com.example.iruka_backend.entity.ReviewIntervalEntity;
// import com.example.iruka_backend.repository.ReviewIntervalRepository;

// @Repository
// public class ReviewIntervalRepositoryImpl {
// @Autowired
// private ReviewIntervalRepository reviewIntervalRepository;

// public ReviewIntervalEntity findByIntervalName(String intervalName) {
// // 実際の処理をここに実装
// return reviewIntervalRepository.findAll().stream()
// .filter(entity -> intervalName.equals(entity.getIntervalName()))
// .findFirst()
// .orElse(null);
// }
// }
