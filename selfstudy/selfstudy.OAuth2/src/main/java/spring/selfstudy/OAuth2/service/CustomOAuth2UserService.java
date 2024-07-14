package spring.selfstudy.OAuth2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationResponse;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import spring.selfstudy.OAuth2.dto.*;
import spring.selfstudy.OAuth2.mapper.SocialUserMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    //DefaultOAUth2UserService OAuth2UserService 의  구현체

    private final SocialUserMapper socialUserMapper;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2user = super.loadUser(userRequest);
        System.out.println(oAuth2user.getAttributes());

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        log.info("{}에서 로그인 인증요청합니다.", registrationId);

        OAuth2Response oAuth2Response = null;

        //네이버와 구글에 경우 보내주는 인증 데이터 규격이 다르기 때문에 분류 -> 대충 : 데이터를 담아낼 그릇이 다르다
        if (registrationId.equals("naver")) {

            oAuth2Response = new NaverResponse(oAuth2user.getAttributes());

        } else if (registrationId.equals("google")) {

            oAuth2Response = new GoogleResponse(oAuth2user.getAttributes());

        } else {
            return null;
        }

        //구현 진행
        String username = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();
        SocialUserDTO existUser = socialUserMapper.findByUserName(username);

        String role = null;
        //db에 등록된 회원인지 체크
        if (existUser == null) {
            SocialUserDTO  socialUser = new SocialUserDTO();
            socialUser.setUsername(username);
            socialUser.setEmail(oAuth2Response.getEmail());
            socialUser.setRole("ROLE_USER");


            socialUserMapper.save(socialUser);
        } else {

            role = existUser.getRole();
            existUser.setEmail(oAuth2Response.getEmail());

            socialUserMapper.updateEmail(existUser);
        }



        return new CustomOAuth2User(oAuth2Response, role);
    }

}
