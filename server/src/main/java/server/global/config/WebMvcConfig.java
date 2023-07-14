package server.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import server.global.util.LoggerInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    // CORS 설정
    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry
                .addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")

                // 프론트 분들 읽을 수 있게 처리
                .exposedHeaders("Access_Token")
                .exposedHeaders("Refresh_Token");
    }

    // 인터셉터
    @Override
    public void addInterceptors (InterceptorRegistry registry) {
        registry.addInterceptor(new LoggerInterceptor())
                .addPathPatterns("/**");
    }
}
