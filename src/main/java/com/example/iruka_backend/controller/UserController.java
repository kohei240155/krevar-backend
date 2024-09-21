package com.example.iruka_backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.iruka_backend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import com.example.iruka_backend.entity.LanguageEntity;
import com.example.iruka_backend.entity.UserSettingsEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import org.slf4j.LoggerFactory;
import com.example.iruka_backend.security.JwtTokenProvider;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/{userId}/settings")
    public ResponseEntity<UserSettingsEntity> getUserSettings(HttpServletRequest httpServletRequest,
            @PathVariable("userId") Long userId) {

        logger.info("------------- ユーザー設定取得API開始 -------------");

        jwtTokenProvider.validateToken(httpServletRequest.getHeader("Authorization"));

        UserSettingsEntity userSettingsEntity = userService.getUserSettings(userId);

        logger.info("------------- ユーザー設定取得API終了 -------------");

        return ResponseEntity.ok(userSettingsEntity);
    }

    @PostMapping("/settings")
    public ResponseEntity<String> saveUserSettings(
            @RequestBody UserSettingsEntity userSettingsEntity) {
        userService.updateUserSettings(userSettingsEntity);
        return ResponseEntity.ok("User settings saved successfully");
    }

    /**
     * 言語一覧取得API
     *
     * @return 言語一覧
     */
    @GetMapping("/language-list")
    public ResponseEntity<List<LanguageEntity>> getLanguageList() {

        logger.info("------------- 言語一覧取得API開始 -------------");

        List<LanguageEntity> languageList = userService.getLanguageList();

        logger.info("------------- 言語一覧取得API終了 -------------");

        return ResponseEntity.ok(languageList);
    }
}
