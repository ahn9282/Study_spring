package spring.study.self.security;

import io.jsonwebtoken.Jwt;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;

public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final Jwt jwt;

    private final UserService userService;

    public JwtAuthenticationProvider(Jwt jwt, UserService userService) {
        this.jwt = jwt;
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }

    /*@Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) authentication;
        return processUserAuthentication(authenticationToken.authenticationRequest());
    }

    private Authentication processUserAuthentication(AuthenticationRequest request) {
        User user = userService.login(new Email(request.getPrincipal()), request.getCredentials());
        JwtAuthenticationToken authenticated = new JwtAuthenticationToken(
                new JwtAuthentication(user.getSeq(), user.getEmail()),
                null,
                AuthorityUtils.createAuthorityList(Role.USER.value()));
        String apiToken = user.newApiToken(jwt, new String[] {Role.USER.value()});
        authenticated.setDetails(new AuthenticationResult(apiToken, user));
        return authenticated;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return isAssignable(JwtAuthenticationToken.class, authentication);
    }
*/
}