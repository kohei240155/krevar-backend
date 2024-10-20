package com.example.krevar_backend.service;

import java.util.List;
import com.example.krevar_backend.entity.LanguageEntity;
import com.example.krevar_backend.entity.UserSettingsEntity;
import com.example.krevar_backend.requestdto.UserSettingsUpdateRequest;

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
    public void updateUserSettings(Long userId,
            UserSettingsUpdateRequest userSettingsUpdateRequest);

    /**
     * 言語一覧を取得する
     *
     * @return 言語一覧
     */
    public List<LanguageEntity> getLanguageList();

    /**
     * サブスクリプションをキャンセルする
     *
     * @param userId ユーザーID
     */
    public void cancelSubscription(Long userId);
}
