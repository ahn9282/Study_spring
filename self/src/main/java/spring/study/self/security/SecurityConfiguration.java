package spring.study.self.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) //우선 공부이기에 csrf 비활성화
               .formLogin(formLogin -> formLogin.loginPage("/login")); //폼로그인도 우선 비활성화

        http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/user/**").permitAll()
                        // /user/ 이후 모든 경로 모든 사용자에게 허용 - responsebody에 "ok"전송
                        .requestMatchers("/login").permitAll()
                        // /login/ 이후 모든 경로 모든 사용자에게 허용 - 로그인 창
                        .requestMatchers("/security-test/**").permitAll()
                        .anyRequest().authenticated() //그외 인증 요구
                )
                .httpBasic(Customizer.withDefaults());
                //기본 HTTP 인증을 사용
        return http.build();// 설정 적용 및 SecurityFilterChain 으로 반환
    }
}