package study.jwt.more.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import study.jwt.more.dto.CustomUserDetails;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

@Slf4j
//jwt 경우 formLogin.disable() 하기 때문에 해당 UsernamePasswordAuthenticationFilter가 안쓰인다.
//해당 UsernamePasswordAuthenticationFilter 에서 Login id, pw 검증을 수행하므로 필수인 필터다.
//그래서 해당 필터를 상속하여 jwt 방식으로 Custom 을 해야만 login 검증을 수행할 수 있다.
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public LoginFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //request 요청에서 username, password 추출
        String username = obtainUsername(request);
        String password = obtainPassword(request);
        log.info("로그인 id = {}, pw = {}", username, password);

        //스프링 시큐리티에서 username, password 를 검증하기 위해 token 으로 담음
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

        //token 에 담은 검증을 위한 AuthenticationManager
        return authenticationManager.authenticate(token);
    }

    //로그인 성공 시 실행하는 메서드
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
//        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
//        String username = customUserDetails.getUsername();
//        Collection<? extends GrantedAuthority> authorities = customUserDetails.getAuthorities();
//        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
//        GrantedAuthority auth = iterator.next();
//
//        String role = auth.getAuthority();
//
//        //jwt 토큰 생성
//        String token = jwtUtil.createJwt(username, role, 60 * 60 * 10L);
//
//        response.addHeader("Authorization", "Bearer " + token);
//        //규약(?) 정의(?) 에 맞춰 토큰을 header에 담기
        // ==================== 기존 jwt 로직 단일 토큰 +====================================

        //==================== 다중 토큰 발급 로직 ====================================
        String username = authentication.getName();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        //토큰 생성
        String access = jwtUtil.createJwt("access", username, role, 600000L);
        String refresh = jwtUtil.createJwt("refresh", username, role, 86400000L);
        response.setHeader("access", access);
        response.addCookie(createRepoCookie("refresh", refresh));
        response.setStatus(HttpStatus.OK.value());
    }


    //로그인 실패 시 실행하는 메서드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(401);

    }

    private Cookie createRepoCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24 * 60 * 60);
       // cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }

}

