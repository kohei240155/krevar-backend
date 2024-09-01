package com.example.iruka_backend.service.impl;

import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.iruka_backend.entity.UserEntity;
import com.example.iruka_backend.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

    // usersテーブルからユーザー情報を取得
    UserEntity user = userRepository.findByEmail(email);

    // ユーザーが見つからない場合は例外をスロー
    if (user == null) {
      throw new UsernameNotFoundException("User not found with email: " + email);
    }

    // ユーザーのロールを設定
    return new org.springframework.security.core.userdetails.User(user.getEmail(),
        user.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));
  }
}
