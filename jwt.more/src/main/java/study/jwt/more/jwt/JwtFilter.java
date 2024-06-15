package study.jwt.more.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.filter.OncePerRequestFilter;
import study.jwt.more.config.SecurityConfig;
import study.jwt.more.domain.UserEntity;
import study.jwt.more.dto.CustomUserDetails;

import java.io.IOException;

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

        String authorization = request.getHeader("Authorization");
        if (authorization == null) {
            //토큰이 null 또는 "Authorization"이 아닌 것으로 시작할 경우 -> 검증 실패
            filterChain.doFilter(request, response);
            log.info("검증 실패 : null");
            return; //메서드 종료
        }
        if( !authorization.startsWith("Bearer")){
            filterChain.doFilter(request, response);
            log.info("검증 실패 : not Bearer ");
            return; //메서드 종료
        }
        String token = authorization.split(" ")[1];//Bearer 토큰 번호 <- 이거 잘라옴
        if (jwtUtil.isExpired(token)) {
            //토큰 만료
            log.info("토큰 만료");
            filterChain.doFilter(request, response);
            return; //메서드 종료
        }

        String username = jwtUtil.getUsername(token);//decoding을 통한 username 추출
        String role = jwtUtil.getRole(token);

        //userEntity 를 생성하여 값 set
        UserEntity user = new UserEntity(username, "temppassword", role);

        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        Authentication authToken =
                new UsernamePasswordAuthenticationToken(new CustomUserDetails(user),
                        null, customUserDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);

        log.info("검증 성공");
        filterChain.doFilter(request, response);
    }
}
