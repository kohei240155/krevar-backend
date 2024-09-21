package com.example.iruka_backend.service.impl;

import java.util.HashMap;
import java.util.Map;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.example.iruka_backend.entity.UserEntity;
import com.example.iruka_backend.entity.UserLoginEntity;
import com.example.iruka_backend.repository.UserRepository;
import com.example.iruka_backend.requestdto.GoogleLoginRequest;
import com.example.iruka_backend.security.JwtTokenProvider;
import com.example.iruka_backend.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    /**
     * Googleログインを処理する
     *
     * @param request Googleログインのペイロード
     * @return Googleログイン成功時のレスポンス
     */
    @Override
    public ResponseEntity<?> googleLogin(GoogleLoginRequest request) {

        // メールアドレスとGoogleIDを取得
        String email = request.getEmail();
        String googleId = request.getGoogleId();
        String name = request.getName();

        boolean isNewUser = false;

        UserEntity user = userRepository.findUserByEmail(email);
        if (user == null) {
            // ユーザーが存在しない場合、新規登録
            UserLoginEntity userLoginEntity = new UserLoginEntity(email, "USER", googleId, name);
            userRepository.saveNewUser(userLoginEntity);
            isNewUser = true;
            user = userRepository.findUserByEmail(email); // 新規登録後にユーザーを再取得
        }

        // JWTを発行
        String jwtToken = jwtTokenProvider.generateToken(email);

        // JWTをクッキーに設定
        ResponseCookie jwtCookie = ResponseCookie.from("JWT", jwtToken).path("/") // クッキーのパスを指定
                .httpOnly(true) // JavaScriptからアクセス不可にする
                .secure(true) // HTTPS接続のみで送信
                .sameSite("None") // Cross-Siteリクエストの防止
                .build();

        logger.info("Google login successful for email: {}. JWT Token: {}", email, jwtToken);

        // レスポンスボディを作成
        Map<String, Object> response = new HashMap<>();
        response.put("userId", user.getId());
        response.put("token", jwtToken);
        response.put("email", email);
        response.put("role", "USER");
        response.put("isNewUser", isNewUser);

        // レスポンスヘッダーにJWTクッキーを追加
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, jwtCookie.toString());

        return ResponseEntity.ok().headers(headers).body(response);
    }

    /**
     * ログアウトを処理する
     *
     * @param session セッション
     */
    @Override
    public ResponseEntity<String> logout(HttpSession session) {
        // 認証情報をクリア
        SecurityContextHolder.clearContext();

        // セッション無効化
        session.invalidate();

        return ResponseEntity.ok("Logout successful");
    }
}
