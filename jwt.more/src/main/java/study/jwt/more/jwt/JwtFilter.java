package study.jwt.more.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.filter.OncePerRequestFilter;
import study.jwt.more.config.SecurityConfig;
import study.jwt.more.domain.UserEntity;
import study.jwt.more.dto.CustomUserDetails;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
//jwt 검증 필터 구현  JwtUtil 내 검증 메서드도 존재
//요청에 대해서 한번만 실행되는 필터를 상속
public class JwtFilter extends OncePerRequestFilter {
        private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//========================= 기존 jwtFilter 코드 =========================================
//        String authorization = request.getHeader("Authorization");
//        //request 객체를 통해 header - authorization key에서 값(value) 꺼냄
//
//        if (authorization == null) {
//            //토큰이 null 또는 "Authorization"이 아닌 것으로 시작할 경우 -> 검증 실패
//            filterChain.doFilter(request, response);
//            log.info("검증 실패 : null");
//            return; //메서드 종료
//        }
//        if( !authorization.startsWith("Bearer")){
//            filterChain.doFilter(request, response);
//            log.info("검증 실패 : not Bearer ");
//            return; //메서드 종료
//        }
//        String token = authorization.split(" ")[1];//Bearer 토큰 번호 <- 이거 잘라옴
//        if (jwtUtil.isExpired(token)) {
//            //토큰 만료
//            log.info("토큰 만료");
//            filterChain.doFilter(request, response);
//            return; //메서드 종료
//        }
//
//        String username = jwtUtil.getUsername(token);//decoding을 통한 username 추출
//        String role = jwtUtil.getRole(token);
//
//        //userEntity 를 생성하여 값 set
//        UserEntity user = new UserEntity(username, "temppassword", role);
//
//        CustomUserDetails customUserDetails = new CustomUserDetails(user);
//
//        Authentication authToken =
//                new UsernamePasswordAuthenticationToken(new CustomUserDetails(user),
//                        null, customUserDetails.getAuthorities());
//
//        SecurityContextHolder.getContext().setAuthentication(authToken);
//
//        log.info("검증 성공");
//        filterChain.doFilter(request, response);

        //==========================  Access 토큰 Filter +==========================

        String accessToken = request.getHeader("access");//헤더에서 access 키에 담긴 토큰 꺼냄

        //토큰이 없다면 다음 필터로 넘김
        if (accessToken == null) {
            filterChain.doFilter(request, response);
            return ;
        }

        //토큰 만료 여부 확인, 만료 시 다음 필터로 안넘김
        try {
            jwtUtil.isExpired(accessToken);
        } catch (ExpiredJwtException e) {

            PrintWriter writer = response.getWriter();
            writer.print("access : token expired");
            //response status code
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String category = jwtUtil.getCategory(accessToken);

        if (!category.equals("access")) {
            PrintWriter writer = response.getWriter();
            writer.print("invalid access token");
            //response status code
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        //username, role 값을 획득
        String username = jwtUtil.getUsername(accessToken);
        String role = jwtUtil.getRole(accessToken);

        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setRole(role);
        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
