package com.example.iruka_backend.service.impl;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.iruka_backend.entity.UserEntity;
import com.example.iruka_backend.entity.UserSettingsEntity;
import com.example.iruka_backend.repository.UserRepository;
import com.example.iruka_backend.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * ユーザーの設定を取得する
     *
     * @param userId ユーザーのID
     * @return ユーザーの設定
     */
    @Override
    public UserSettingsEntity getUserSettings(Long userId) {

        UserEntity user = userRepository.findUserByUserId(userId);

        String userName = user.getName();
        int defaultNativeLanguageId = user.getDefaultNativeLanguageId();
        int defaultLearningLanguageId = user.getDefaultLearningLanguageId();
        int imageGenerationRemaining = user.getImageGenerationRemaining();
        LocalDate imageGenerationResetDate = user.getImageGenerationResetDate();
        int subscriptionStatusId = user.getSubscriptionStatusId();
        String highlightColor = user.getHighlightColor();

        UserSettingsEntity userSettingsEntity = new UserSettingsEntity(userName,
                defaultNativeLanguageId, defaultLearningLanguageId, imageGenerationRemaining,
                imageGenerationResetDate, subscriptionStatusId, highlightColor);

        return userSettingsEntity;
    }

    /**
     * ユーザーの情報を更新する
     *
     * @param userSettingsEntity ユーザーの設定情報
     */
    @Override
    public void updateUserSettings(UserSettingsEntity userSettingsEntity) {
        userRepository.update(userSettingsEntity);
    }
}
