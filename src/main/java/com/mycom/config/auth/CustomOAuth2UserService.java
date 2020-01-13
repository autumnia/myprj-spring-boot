package com.mycom.config.auth;

import com.mycom.config.auth.dto.*;
import com.mycom.sample.domains.user.*;

import lombok.*;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.*;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.*;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();  // 구글, 네이버, 기타 서비스 구분시 필요
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();  // 구글, 네아버, 기타 서비스 동시 사용시 필요함

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());  // 모든 소셜 네트워크가 이용함

        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user));  // 세션에 사용자 정보를 사용하기 위한 DTO 입니다.

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private User saveOrUpdate( OAuthAttributes attributes ) {
        User user = userRepository.findByEmail( attributes.getEmail() )
                .map( entity -> entity.update( attributes.getName(), attributes.getPicture() ) )
                .orElse( attributes.toEntity() );

        return userRepository.save(user);
    }
}
