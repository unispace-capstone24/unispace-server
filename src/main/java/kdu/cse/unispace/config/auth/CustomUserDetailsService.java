package kdu.cse.unispace.config.auth;

import kdu.cse.unispace.domain.Member;
import kdu.cse.unispace.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다. email=" + email));

        return new CustomUserDetails(
                member.getId(),
                member.getEmail(),
                member.getPassword(),
                member.getMemberName(),
                true,
                null // 권한 정보가 필요할 경우, 설정해줘야 함
        );
    }
}
