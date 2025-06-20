package com.example.krevar_backend.repository;

import java.util.List;
import com.example.krevar_backend.entity.LanguageEntity;
import com.example.krevar_backend.entity.SubscriptionEntity;
import com.example.krevar_backend.entity.UserEntity;
import com.example.krevar_backend.entity.UserLoginEntity;
import com.example.krevar_backend.entity.UserSubscriptionEntity;
import com.example.krevar_backend.requestdto.UserSettingsUpdateRequest;

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
  void update(Long userId, UserSettingsUpdateRequest userSettingsUpdateRequest);

  /**
   * ユーザーをメールアドレスで検索する
   *
   * @param email メールアドレス
   * @return ユーザー
   */
  UserEntity findUserByEmail(String email);

  /**
   * 言語一覧を取得する
   *
   * @return 言語一覧
   */
  List<LanguageEntity> findAllLanguage();

  /**
   * ユーザーの支払い情報を保存する
   *
   * @param userSubscription ユーザーの支払い情報
   */
  void saveUserSubscription(UserSubscriptionEntity userSubscription);

  /**
   * ユーザーの支払い情報を更新する
   *
   * @param userId ユーザーのID
   * @param subscription サブスクリプション
   */
  void cancelSubscription(Long userId, SubscriptionEntity subscription);
}
