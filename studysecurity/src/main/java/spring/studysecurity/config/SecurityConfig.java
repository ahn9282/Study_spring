package spring.studysecurity.config;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
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
                //.csrf((auth) -> auth.disable())
                .authorizeHttpRequests((auth) -> auth  //Security 인가 작업
                        .requestMatchers("/login", "/join", "loginProc", "joinProc").permitAll()
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/my/**", "/logout").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/admin").hasAnyRole("ADMIN")
                        .anyRequest().authenticated()
                )
//                .formLogin((auth) -> auth
//                        .loginPage("/login")
//                        .loginProcessingUrl("/loginProc")//로그인 처리 url
//                        .permitAll() )
                .httpBasic(Customizer.withDefaults())
                //http basic 방식의 로그인 form 태그로 로그인하는 방식이 아님

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
                        .deleteCookies("JSESSIONID"))

                //중복 로그인 설정
                .sessionManagement((auth) -> auth
                        //최대 로그인 수 설정 다중 로그인 허용 개수 1개
                        .maximumSessions(1)
                        //다중 로그인 개수 초과 시 설정
                        //true :  초과 시 새로운 로그인 차단
                        //false : 초과 시 기존 세션 하나 삭제
                        .maxSessionsPreventsLogin(true))

                //세션 고정 공격 보호 설정
                .sessionManagement((auth) -> auth
                        //로그인 시 세션 정보 변경 X
                        //.sessionFixation().none()
                        //로그인 시 세션 새로 생성
                        // .sessionFixation().newSession()
                        // 로그인 시 동일한 세션에 대한 id 변경
                        .sessionFixation().changeSessionId());

        return http.build();
    }

    @Bean//암호화 모듈 메서드 추가 Bean등록
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
