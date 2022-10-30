package news.data.collect.pro.config;

import com.sun.tools.javac.util.List;
import lombok.RequiredArgsConstructor;
import news.data.collect.pro.commons.oauth.CustomOAuth2UserService;
import news.data.collect.pro.commons.oauth.HttpCookieOAuth2AuthorizationRequestRepository;
import news.data.collect.pro.commons.oauth.OAuth2AuthenticationSuccessHandler;
import news.data.collect.pro.commons.oauth.Oauth2AuthenticationFailureHandler;
import news.data.collect.pro.domains.auth.service.AuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final HttpCookieOAuth2AuthorizationRequestRepository oAuth2AuthorizationRequestRepository;

    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    private final Oauth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
         http
                 .csrf().disable();


         http
                 .authorizeRequests()
                 .antMatchers("/api/v1/**").permitAll()
                 .antMatchers("/main").authenticated()
                 .antMatchers("/setting").authenticated()
                 .antMatchers("/static/**").permitAll()
                 .anyRequest().permitAll()
                 .and();


        http
                .oauth2Login()//oAuth2 설정 시작
                .loginPage("/")
                .authorizationEndpoint()
                .baseUri("/oauth2/authorize")
                .authorizationRequestRepository(oAuth2AuthorizationRequestRepository)
                .and()
                .redirectionEndpoint()
                .baseUri("/oauth2/callback/*")
                .and()
                .userInfoEndpoint().userService(customOAuth2UserService)
                .and()
                .successHandler(oAuth2AuthenticationSuccessHandler)
                .failureHandler(oAuth2AuthenticationFailureHandler);
        http
                .cors().configurationSource(corsConfigurationSource());

        return http.build();
    }

    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowedOrigins(List.of("*"));
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }

}
