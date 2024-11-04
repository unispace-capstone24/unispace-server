package kdu.cse.unispace.validation.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import kdu.cse.unispace.exception.RequiredValueMissingException;
import kdu.cse.unispace.exception.member.join.DuplicateEmailException;
import kdu.cse.unispace.exception.member.join.InvalidPasswordException;
import kdu.cse.unispace.requestdto.member.MemberJoinRequestDto;
import kdu.cse.unispace.service.MemberService;

@RequiredArgsConstructor
@Component
public class MemberJoinValidator implements Validator {
    private final MemberService memberService;
    private static final int MIN_PASSWORD_LENGTH = 6;

    @Override
    public boolean supports(Class<?> clazz) {
        return MemberJoinRequestDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MemberJoinRequestDto requestDto = (MemberJoinRequestDto) target;

        if (requestDto.getEmail() == null || requestDto.getEmail().trim().isEmpty() ||
                requestDto.getMemberName() == null || requestDto.getMemberName().trim().isEmpty() ||
                requestDto.getPassword() == null || requestDto.getPassword().trim().isEmpty()) {
            throw new RequiredValueMissingException("필수 정보가 없습니다.");
        }

        if (requestDto.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidPasswordException("비밀번호는 최소 " + MIN_PASSWORD_LENGTH + "자 이상이어야 합니다.");
        }

        if (memberService.existsByEmail(requestDto.getEmail())) {
            throw new DuplicateEmailException("이미 존재하는 email 입니다.");
        }
    }
}
