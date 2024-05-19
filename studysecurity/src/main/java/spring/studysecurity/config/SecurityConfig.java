package spring.studysecurity.config;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Slf4j
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf((auth) -> auth.disable())
                .authorizeHttpRequests((auth) -> auth  //Security 인가 작업
                        .requestMatchers("/login", "/join","loginProc", "joinProc").permitAll()
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/my/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/admin").hasAnyRole("ADMIN")
                        .anyRequest().authenticated()


                )
                .formLogin((auth) -> auth
                        .loginPage("/login")
                        .loginProcessingUrl("/loginProc")//로그인 처리 url
                        .permitAll() )

                .logout(logout -> logout
                        .logoutUrl("/logout")//logout 요청 처리 uRL
                        .addLogoutHandler((request, response, authentication) -> {
                            //로그아웃 시  실행 핸들러
                            HttpSession session = request.getSession();
                            if (session != null) {
                                session.invalidate();
                            }
                        }).logoutSuccessHandler(((request, response, authentication) -> {
                            response.sendRedirect("/login");// 로그아웃 성공 시 리다이렉트
                        }))//로그 아웃 성공 핸들러
                        .deleteCookies("JSESSIONID"));

        return http.build();
    }

    @Bean//암호화 모듈 메서드 추가 Bean등록
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
