package com.example.iruka_backend.util;

import com.example.iruka_backend.responsedto.ErrorResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException {

        // エラーレスポンスを構築
        ErrorResponse errorResponse = new ErrorResponse(HttpServletResponse.SC_UNAUTHORIZED, // 401
                "Invalid JWT token", Instant.now().toEpochMilli() // 現在のタイムスタンプをミリ秒で取得
        );

        // JSON形式でレスポンスを返す設定
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // ステータスコードを設定
        response.getWriter().write(convertObjectToJson(errorResponse)); // JSONをレスポンスボディに書き込む
    }

    // ErrorResponseオブジェクトをJSONに変換するヘルパーメソッド
    private String convertObjectToJson(ErrorResponse errorResponse) throws IOException {
        // ObjectMapperを使用してオブジェクトをJSONに変換
        return new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(errorResponse);
    }
}
