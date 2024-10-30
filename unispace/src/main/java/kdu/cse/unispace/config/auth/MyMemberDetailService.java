package kdu.cse.unispace.config.auth;

import kdu.cse.unispace.domain.Member;
import kdu.cse.unispace.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MyMemberDetailService implements UserDetailsService {

    private final MemberService memberService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberService.findByEmail(email);

        if (member == null) {
            throw new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다. email=" + email);
        }

        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles("USER")
                .build();
    }



    public UserDetails getUserDetailsByEmail(String email) throws UsernameNotFoundException {
        return loadUserByUsername(email);
    }

}