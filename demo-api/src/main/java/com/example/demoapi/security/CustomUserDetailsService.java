package com.example.demoapi.security;

import jakarta.annotation.Resource;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));  // 使用 SimpleGrantedAuthority

        // 在實際應用中，您可以從資料庫或其他存儲中加載使用者信息
        // 這裡簡單起見，我們創建一個使用者
        UserDetails user = User.builder()
                .username("admin")
                .password(("{noop}password")) // {noop} 表示不使用密碼編碼器
                .roles("ADMIN") // 分配 ADMIN 角色
                .authorities(authorities )
                .build();
        return user;
    }
}