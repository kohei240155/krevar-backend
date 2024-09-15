package com.example.iruka_backend.service;

import org.springframework.http.ResponseEntity;
import com.example.iruka_backend.requestdto.GoogleLoginRequest;
import jakarta.servlet.http.HttpSession;

public interface AuthService {
    /**
     * Googleログインを処理する
     *
     * @param payload Googleログインのペイロード
     * @return Googleログイン成功時のレスポンス
     */
    ResponseEntity<?> googleLogin(GoogleLoginRequest request);

    /**
     * ログアウトを処理する
     *
     * @param session セッション
     * @return ログアウト成功時のレスポンス
     */
    ResponseEntity<String> logout(HttpSession session);
}
