package org.ContinuityIns.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    /**
     * 跨域配置
     * @date 2024/11/9
     * @Author  Oii Woof
        */

    @Value("${org.ContinuityIns.url}")
    private String URL;

    @Bean
    public CorsFilter corsFilter() {
        // 添加CORS配置信息
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin(URL);
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.setMaxAge(1800L); // 预检请求的缓存时间（秒）
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}