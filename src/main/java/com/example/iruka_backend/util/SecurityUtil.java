package com.example.iruka_backend.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {

    public static String getAuthenticatedUsername() {

        // 認証情報を取得
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 認証情報がある場合はユーザー名を返す
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            // ユーザー名を返す
            return ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        // 認証情報がない場合はnullを返す
        return null;
    }
}
