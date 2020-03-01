package com.mycom.config;

import com.mycom.config.auth.CustomOAuth2UserService;
import com.mycom.sample.domains.user.Role;
import lombok.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
            http
                // h2-console 화면을 보기 위해 csrf disable 시킴  나중에는 제거 필요함
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                    .antMatchers("/", "/css/**", "/images/**", "/js/**", "/profile").permitAll()
                    .antMatchers("/api/v1/**").hasRole( Role.USER.name() )
                    .anyRequest().authenticated() // 위 설정된 값을 제외한 모든 url 적용
//                .and()
//                .csrf()
//                    .csrfTokenRepository( CookieCsrfTokenRepository.withHttpOnlyFalse() )
                .and()
                    .logout()
                        .logoutSuccessUrl("/") // 로그아웃 성공 후 돌아갈 주소
                .and()
                    .oauth2Login()
                        .userInfoEndpoint()
                            .userService(customOAuth2UserService);  // 소셜 서비스 로그인 후 사용자 정보를 저장할  사용자 정의 클래스
    }
}