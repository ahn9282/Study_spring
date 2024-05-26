package self.study.jwt.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import self.study.jwt.jwt.JWTUtil;
import self.study.jwt.jwt.JwtFilter;
import self.study.jwt.jwt.LoginFilter;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    //AuthenticationManager가 인자로 박을 AuthenticationConfiguration 객체 생성자 주입
    private final AuthenticationConfiguration configuration;
    private final JWTUtil jwtUtil;

    public SecurityConfig(AuthenticationConfiguration configuration, JWTUtil jwtUtil) {
        this.configuration = configuration;
        this.jwtUtil = jwtUtil;
    }

    //Authentication Bean 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf((auth) -> auth.disable());

        http
                .formLogin((auth) -> auth.disable());

        http
                .httpBasic((auth) -> auth.disable());

        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/login", "/", "/join").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .anyRequest().authenticated());

        http.addFilterBefore(new JwtFilter(jwtUtil), LoginFilter.class);


        http        //필터에는 인자를 필요로함 이는 위에 추가하였음
                //AuthenticationManager라는 객체를 인자로 받음
                // + JWTUtil 주입
                .addFilterAt(new LoginFilter(authenticationManager(configuration), jwtUtil), UsernamePasswordAuthenticationFilter.class);
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //cors설정
        http
                .cors(cors -> cors
                        .configurationSource(new CorsConfigurationSource() {
                            @Override
                            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                                CorsConfiguration corsConfiguration = new CorsConfiguration();
                                //프론트 엔드단 포트 번호 cors 적용 설정
                                corsConfiguration.setAllowedOrigins(Collections.singletonList("http://localgost:3000"));
                                //http 메서드 허용 설정
                                corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
                                //
                                corsConfiguration.setAllowCredentials(true);
                                //허용할 header 설정
                                corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
                                corsConfiguration.setMaxAge(3600L);

                                //Authorization header에 jwt토큰을 보내기 때문에 header를 허용 설정
                                corsConfiguration.setExposedHeaders(Collections.singletonList("Authorization"));

                                return corsConfiguration;
                            }
                        }));

        return http.build();
    }


}
