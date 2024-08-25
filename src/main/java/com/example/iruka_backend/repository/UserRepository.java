package com.example.iruka_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.iruka_backend.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    // usersテーブルからユーザー情報を取得
    UserEntity findByEmail(String email);
}