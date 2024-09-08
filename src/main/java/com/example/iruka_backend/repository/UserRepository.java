package com.example.iruka_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.iruka_backend.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

  /**
   * ユーザーをメールアドレスで検索する
   *
   * @param email メールアドレス
   * @return ユーザー
   */
  UserEntity findByEmail(String email);
}
