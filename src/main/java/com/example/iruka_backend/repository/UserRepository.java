package com.example.iruka_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.iruka_backend.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
  UserEntity findByEmail(String email);
  // 他のメソッドの宣言をここに追加
}
