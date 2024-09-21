package com.example.iruka_backend.service;

import com.example.iruka_backend.entity.UserSettingsEntity;

public interface UserService {

    /**
     * ユーザーの設定を取得する
     *
     * @param userId ユーザーのID
     * @return ユーザーの設定
     */
    public UserSettingsEntity getUserSettings(Long userId);

    /**
     * ユーザーの情報を更新する
     *
     * @param user ユーザー
     */
    public void updateUserSettings(UserSettingsEntity userSettingsEntity);
}
