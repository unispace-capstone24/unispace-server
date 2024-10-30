package kdu.cse.unispace.config.auth;

import jakarta.servlet.http.HttpServletResponse;
import kdu.cse.unispace.config.jwt.JwtAuthenticationFilter;
import kdu.cse.unispace.config.jwt.JwtAuthenticationSuccessHandler;
import kdu.cse.unispace.domain.Member;
import kdu.cse.unispace.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Configuration
@EnableWebSecurity // Spring Security 설정 활성화
@RequiredArgsConstructor
//@EnableGlobalAuthentication
@EnableGlobalMethodSecurity(prePostEnabled = true) //@PreAuthorize @PostAuthorize 활성화
public class SecurityConfig {


    private final MyMemberDetailService myMemberDetailService;

    private final GoogleOauth2UserService googleOauth2UserService;


    private final OAuth2AuthorizedClientService oath2;
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private final JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final MemberService memberService;



//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS"));
//        configuration.setAllowedHeaders(Arrays.asList("*"));
//        configuration.setAllowCredentials(true);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()  //csrf disable
                //.cors().disable()
                //.cors().and()

                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(requests -> requests //로그인 전
                        //.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/", "/home", "/signup", "/member", "/login","/oauth2/**", "/profile").permitAll()
                        .requestMatchers("/ws/**", "/sub/**", "/pub/**").permitAll()

                        //.anyRequest().permitAll() //임시로 모든 페이지 접근 허용

                        .anyRequest().authenticated() // 어떠한 요청이라도 인증이 필요
                )


                //일반 로그인
                .formLogin((form) -> form // form 방식 로그인 사용
                        .loginPage("/login")  //로그인 페이지
                        .loginProcessingUrl("/login-process") //submit을 받을 url - 시큐리티가 처리해줌(MyMemberDetailService로 넘겨준것을)
                        .usernameParameter("email") //submit할 아이디(이메일)
                        .passwordParameter("password") //submit할 비밀번호
                        //.defaultSuccessUrl("/login", true) //성공시
                        .failureUrl("/login") //로그인 실패시 다시 로그인화면
                        .permitAll()  // 로그인 페이지 이동이 막히면 안되므로 관련된애들 모두 허용
                        .successHandler(jwtAuthenticationSuccessHandler)
                )


                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/oauth2/authorization/google")
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(googleOauth2UserService)
                        )
                        .defaultSuccessUrl("/main", true) //성공시
                        .permitAll()
                )


                .logout() // 로그아웃시 /로 이동

                .logoutSuccessHandler((request, response, authentication) -> {
                    if (authentication != null && authentication.getDetails() != null) {
                        try {
                            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
                            Member member = memberService.findOne(customUserDetails.getId());
                            member.changeUuid(UUID.randomUUID()); // change UUID to invalidate all tokens
                            memberService.save(member); // persist the updated user record
                            request.getSession().invalidate(); // invalidate session
                            response.getWriter().write("로그아웃 완료");
                            response.setStatus(HttpServletResponse.SC_OK);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })

//                    .logoutSuccessHandler((request, response, authentication) -> {
//                        response.getWriter().write("로그아웃 완료");
//                        response.setStatus(HttpServletResponse.SC_OK);
//                    });


        //.logout(logout->logout.logoutSuccessUrl("/")) // 로그아웃시 /로 이동


        //.httpBasic()//postman 사용시 필요

        ;
        return http.build();
    }

}