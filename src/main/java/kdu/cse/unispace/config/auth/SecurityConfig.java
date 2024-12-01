package kdu.cse.unispace.config.auth;

import kdu.cse.unispace.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity // Spring Security 설정 활성화
@RequiredArgsConstructor
//@EnableGlobalAuthentication
public class SecurityConfig {


    private final MyMemberDetailService myMemberDetailService;

    private final GoogleOauth2UserService googleOauth2UserService;


    private final OAuth2AuthorizedClientService oath2;
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    private final MemberService memberService;



    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/", "/home", "/signup", "/member", "/login", "/profile").permitAll() // 로그인 전 접근 가능
                        .anyRequest().authenticated() // 그 외 모든 요청은 인증 필요
                )
                // 일반 로그인 설정
                .formLogin(form -> form
                        .loginPage("/login")  // 로그인 페이지 경로
                        .loginProcessingUrl("/login-process") // 로그인 폼 제출 URL
                        .usernameParameter("email") // 이메일 필드 이름
                        .passwordParameter("password") // 비밀번호 필드 이름
                        .defaultSuccessUrl("/home", true) // 로그인 성공 시 이동할 페이지
                        .failureUrl("/login?error=true") // 로그인 실패 시 이동할 페이지
                        .permitAll() // 로그인 관련 URL은 인증 없이 접근 가능
                )
                // 로그아웃 설정
                .logout(logout -> logout
                        .logoutUrl("/logout") // 로그아웃 URL
                        .logoutSuccessUrl("/") // 로그아웃 성공 시 이동할 URL
                        .invalidateHttpSession(true) // 세션 무효화
                        .deleteCookies("JSESSIONID") // 쿠키 삭제
                );

        return http.build();
    }
}
