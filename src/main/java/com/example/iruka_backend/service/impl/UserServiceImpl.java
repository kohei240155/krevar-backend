package com.example.iruka_backend.service.impl;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.iruka_backend.entity.LanguageEntity;
import com.example.iruka_backend.entity.UserEntity;
import com.example.iruka_backend.entity.UserSettingsEntity;
import com.example.iruka_backend.repository.UserRepository;
import com.example.iruka_backend.requestdto.UserSettingsUpdateRequest;
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
     * @param userId ユーザーのID
     * @param userSettingsUpdateEntity ユーザーの設定情報
     */
    @Override
    public void updateUserSettings(Long userId,
            UserSettingsUpdateRequest userSettingsUpdateRequest) {
        userRepository.update(userId, userSettingsUpdateRequest);
    }

    /**
     * 言語一覧を取得する
     *
     * @return 言語一覧
     */
    @Override
    public List<LanguageEntity> getLanguageList() {
        return userRepository.findAllLanguage();
    }
}
