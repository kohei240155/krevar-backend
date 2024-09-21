package com.example.iruka_backend.repository;

import com.example.iruka_backend.entity.UserEntity;
import com.example.iruka_backend.entity.UserLoginEntity;
import com.example.iruka_backend.entity.UserSettingsEntity;

public interface UserRepository {

  /**
   * ユーザーの設定を取得する
   *
   * @param userId ユーザーのID
   * @return ユーザーの設定
   */
  UserEntity findUserByUserId(Long userId);

  /**
   * ユーザを新規登録する
   *
   * @param user ユーザー
   */
  void saveNewUser(UserLoginEntity user);

  /**
   * ユーザー情報を更新する
   *
   * @param user ユーザー
   */
  void update(UserSettingsEntity userSettingsEntity);

  /**
   * ユーザーをメールアドレスで検索する
   *
   * @param email メールアドレス
   * @return ユーザー
   */
  UserEntity findUserByEmail(String email);
}
