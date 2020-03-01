package com.mycom.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycom.config.auth.LoginUserArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.mycom.common.HtmlCharacterEscapes;
import com.mycom.interceptor.*;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final LoginUserArgumentResolver loginUserArgumentResolver;

    //  로그인 세션 반복 처리 금지  모든 controller에서 사용 가능
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(loginUserArgumentResolver);
    }

    // Cross domain 처리
    @Override
    public void addCorsMappings( CorsRegistry registry ) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedOrigins("http://localhost:8080","http://localhost:8081", "http://localhost:8082" )
                .maxAge(3600) ;
    }

    // XSS 처리
    @Override
    public void configureMessageConverters( List<HttpMessageConverter<?>> converters ) {
        converters.add( escapingConverter() );
    }

    @Bean
    public HttpMessageConverter escapingConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.getFactory().setCharacterEscapes( new HtmlCharacterEscapes() );

        MappingJackson2HttpMessageConverter escapingConverter =  new MappingJackson2HttpMessageConverter();
        escapingConverter.setObjectMapper( objectMapper );

        return escapingConverter;
    }

    // 로거 인터셉터
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new LoggerInterceptor());
    }

}
