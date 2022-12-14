package com.justedlev.auth.configuration;

import com.justedlev.auth.properties.CorsProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@RequiredArgsConstructor
public class WebConfiguration implements WebMvcConfigurer {
    private final CorsProperties corsProperties;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOrigins(corsProperties.getOrigins())
                .allowedHeaders("*")
                .exposedHeaders(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS)
                .allowedMethods("*");
//        registry.addMapping("/**")
//                .allowCredentials(true)
//                .allowedOrigins(corsProperties.getOrigins())
//                .allowedHeaders(
//                        "X-Requested-With",
//                        HttpHeaders.CONTENT_TYPE,
//                        HttpHeaders.AUTHORIZATION,
//                        HttpHeaders.ACCEPT,
//                        HttpHeaders.ORIGIN,
//                        HttpHeaders.COOKIE,
//                        HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,
//                        HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS)
//                .exposedHeaders(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS)
//                .allowedMethods(
//                        HttpMethod.GET.name(),
//                        HttpMethod.POST.name(),
//                        HttpMethod.PUT.name(),
//                        HttpMethod.PATCH.name(),
//                        HttpMethod.DELETE.name(),
//                        HttpMethod.OPTIONS.name()
//                );
    }
}
