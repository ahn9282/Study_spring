package spring.study.self.security;

import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) 
               .formLogin(formLogin -> formLogin.loginPage("/login")
                       .usernameParameter("username")//id 파라미터 이름 설정
                       .passwordParameter("password")//password 파라미터 이름 설정
                       .defaultSuccessUrl("/",true));
                        //성공시 위 url로 기본적 리다이렉트

        http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/user/**").hasRole("user")

                        .requestMatchers("/login").permitAll()

                        .requestMatchers("/security-test/**").permitAll()
                        .requestMatchers("/register").permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());

        http.logout(logout -> logout
                .logoutUrl("/logout")//logout 요청 처리 uRL
                .addLogoutHandler((request, response, authentication) -> {
                    //로그아웃 시  실행 핸들러
                    HttpSession session = request.getSession();
                    if (session != null) {
                    //사실 LogoutFilter가 내부적으로 세션은 무효화를 해주긴 한다.
                    ///세션 무효화
                        session.invalidate();
                    }
                }).logoutSuccessHandler(((request, response, authentication) -> {
                    response.sendRedirect("/login");// 로그아웃 성공 시 리다이렉트
                }))//로그 아웃 성공 핸들러
                .deleteCookies("JSESSIONID"));
                //로그아웃 후 쿠키 JSESSIONID 삭제


        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        manager.createUser(User.withUsername("testuser").password("1111").roles("user").build());
        return manager;
    }

    //패스워드 암호화
    @Bean
    public PasswordEncoder passwordEncoder(){
        //Spring Security는 여러개의 암호화 모듈을 제공
        return NoOpPasswordEncoder.getInstance();//공부 이기에 암호화 적용X
    }
}