package kdu.cse.unispace.config.auth;

import kdu.cse.unispace.domain.Member;
import kdu.cse.unispace.repository.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Component //Google OAuth2 API를 사용하여 사용자 정보를 가져옴
public class GoogleOauth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User>{

    @Autowired
    MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // OAuth2UserRequest에서 액세스 토큰을 가져옴
        OAuth2AccessToken accessToken = userRequest.getAccessToken();

        // 액세스 토큰을 사용하여 OAuth2User를 가져옴
        DefaultOAuth2UserService oauth2UserService = new DefaultOAuth2UserService();
        OAuth2User oauth2User = oauth2UserService.loadUser(userRequest);

        // 사용자 정보 가져옴
        String email = (String) oauth2User.getAttribute("email");


        Optional<Member> findUser = memberRepository.findByEmail(email);



        if (findUser.isEmpty()) {
            // email을 바탕으로 회원 엔티티 생성
            String name =oauth2User.getAttribute("name");
            Member member = new Member(UUID.randomUUID(), name, email, null);
            memberRepository.save(member);
        }
        return new DefaultOAuth2User(new ArrayList<>(oauth2User.getAuthorities()),
                oauth2User.getAttributes(), "email");
    }


}