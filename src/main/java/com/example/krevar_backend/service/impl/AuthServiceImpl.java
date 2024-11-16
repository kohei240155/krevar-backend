package com.example.krevar_backend.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Collections;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.example.krevar_backend.entity.SubscriptionEntity;
import com.example.krevar_backend.entity.UserEntity;
import com.example.krevar_backend.entity.UserLoginEntity;
import com.example.krevar_backend.repository.UserRepository;
import com.example.krevar_backend.repository.SubscriptionRepository;
import com.example.krevar_backend.requestdto.GoogleLoginRequest;
import com.example.krevar_backend.security.JwtTokenProvider;
import com.example.krevar_backend.service.AuthService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.http.HttpStatus;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private static final String CLIENT_ID =
            "549087715879-tpv0is8ctl7hc6mv34lslehhcjil1qdc.apps.googleusercontent.com";

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
        String idToken = request.getIdToken();

        try {
            // IDトークンの検証
            GoogleIdTokenVerifier verifier =
                    new GoogleIdTokenVerifier.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                            JacksonFactory.getDefaultInstance())
                                    .setAudience(Collections.singletonList(CLIENT_ID)).build();

            GoogleIdToken token = verifier.verify(idToken);

            if (token == null) {
                throw new IllegalArgumentException("Invalid ID token.");
            }

            GoogleIdToken.Payload payload = token.getPayload();
            String googleIdFromToken = payload.getSubject();

            boolean isNewUser = false;

            UserEntity user = userRepository.findUserByEmail(email);
            if (user == null) {
                // ユーザーが存在しない場合、新規登録
                // サブスクリプションを取得
                SubscriptionEntity subscription = subscriptionRepository.getSubscription("trial");

                UserLoginEntity userLoginEntity = new UserLoginEntity(email, "USER", googleId, name,
                        subscription.getMaxImageGeneration(), subscription.getId());
                userRepository.saveNewUser(userLoginEntity);
                isNewUser = true;
                user = userRepository.findUserByEmail(email);
            }

            String userId = String.valueOf(user.getId());

            // JWTを発行
            String jwtToken = jwtTokenProvider.generateToken(email, userId);

            // JWTをクッキーに設定
            ResponseCookie jwtCookie = ResponseCookie.from("JWT", jwtToken).path("/") // クッキーのパスを指定
                    .httpOnly(true) // JavaScriptからアクセス不可にする
                    .secure(false) // HTTPS接続のみで送信 (開発環境では false に設定、HTTPS環境では true)
                    .sameSite("None") // クロスサイトリクエストの許可
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

        } catch (Exception e) {
            logger.error("Google login failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Google login failed");
        }

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
