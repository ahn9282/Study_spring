package spring.study.self.security;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private UserService userService;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .formLogin(formLogin -> formLogin.loginPage("/login")
                        .usernameParameter("username")//id 파라미터 이름 설정
                        .passwordParameter("password")//password 파라미터 이름 설정
                        .defaultSuccessUrl("/", true))

                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/user/**").hasRole("USER")
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/security-test/**").permitAll()
                        .requestMatchers("/register").permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())

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

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("testuser")
                .password(passwordEncoder.encode("1111"))
                .roles("USER")
                .build());
        return manager;
    }

    //패스워드 암호화
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}