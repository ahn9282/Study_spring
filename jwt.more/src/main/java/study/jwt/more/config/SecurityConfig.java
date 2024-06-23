package study.jwt.more.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import study.jwt.more.jwt.JwtFilter;
import study.jwt.more.jwt.JwtUtil;
import study.jwt.more.jwt.LoginFilter;
import study.jwt.more.oauth2.CustomSuccessHandler;
import study.jwt.more.service.CustomOAuth2UserService;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final AuthenticationConfiguration configuration;
    //AuthenticationManager 에 주입할 AuthenticationConfiguration 주입
    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomSuccessHandler customSuccessHandler;

    public SecurityConfig(JwtUtil jwtUtil, AuthenticationConfiguration configuration, CustomOAuth2UserService customOAuth2UserService, CustomSuccessHandler customSuccessHandler) {
        this.jwtUtil = jwtUtil;
        this.configuration = configuration;
        this.customOAuth2UserService = customOAuth2UserService;
        this.customSuccessHandler = customSuccessHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf((auth) -> auth.disable());

        http
                .formLogin(auth -> auth.disable());
        http
                .httpBasic((auth) -> auth.disable());

//        http
//                .oauth2Login(oauth2 -> oauth2
//                        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
//                                .userService(customOAuth2UserService))
//                        .successHandler(customSuccessHandler));//oauth2 로그인 커스텀 서비스 등록
//                            //oauth2 로그인 커스텀 successhandler 등록


        http    //cors : 교차 리소스 공유  설정  securityConfig 랑 WebMvcConfigurer 구현체로 두번 설정해야함
                .cors(cors -> cors
                        .configurationSource(new CorsConfigurationSource() {
                            @Override
                            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                                CorsConfiguration corsConfiguration = new CorsConfiguration();
                                //port
                                corsConfiguration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
                                corsConfiguration.setAllowedMethods(Collections.singletonList("*"));//http 메서드
                                corsConfiguration.setAllowCredentials(true);
                                corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));//Header 허용
                                corsConfiguration.setMaxAge(3600L);//최대 시간
                                corsConfiguration.setExposedHeaders(Collections.singletonList("Authorization"));//header key 허용

                                return corsConfiguration;
                            }

                        }));

        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/login", "/", "/join","/reissue").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .anyRequest().authenticated());

      // OAuth2로 인한 로그인 방식이기에 Login Filtet가 따로 필요하질 않음
       http    //jwt 로 인해 새로 커스텀한 필터 추가 UsernamePasswordAuthenticationFilter의 위치로 설정
                .addFilterAt(new LoginFilter(authenticationManager(configuration),jwtUtil), UsernamePasswordAuthenticationFilter.class);
                //기존 필터에서 교체 시 에는 addFilterAt사용

        http    //LoginFilter 실행 전으로 순서지정하여 이미 로그인 시 처리하여 로그인 필터로 반복 없앰
                .addFilterBefore(new JwtFilter(jwtUtil), LoginFilter.class);

        http
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    //필터 추가에 사용될 주입을 위한 Bean 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
