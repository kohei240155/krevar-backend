package com.example.iruka_backend.service;

import org.springframework.http.ResponseEntity;
import com.example.iruka_backend.requestdto.LoginRequest;
import com.example.iruka_backend.requestdto.SignUpRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;

public interface AuthService {

    /**
     * ログインする
     *
     * @param loginRequest ログインリクエスト
     * @param session セッション
     * @return ログイン成功時のレスポンス
     */
    ResponseEntity<?> login(LoginRequest loginRequest, HttpSession session);

    /**
     * ユーザーを登録する
     *
     * @param signUpRequest ユーザー登録リクエスト
     * @return ユーザー登録成功時のレスポンス
     */
    ResponseEntity<?> registerUser(SignUpRequest signUpRequest);

    /**
     * Googleログインを処理する
     *
     * @param payload Googleログインのペイロード
     * @param request HTTPリクエスト
     * @return Googleログイン成功時のレスポンス
     */
    ResponseEntity<?> googleLogin(Map<String, Object> payload, HttpServletRequest request);
}
