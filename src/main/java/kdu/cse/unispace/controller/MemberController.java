package kdu.cse.unispace.controller;

import kdu.cse.unispace.config.jwt.JwtAuthenticationFilter;
import kdu.cse.unispace.domain.Member;
import kdu.cse.unispace.requestdto.member.MemberJoinRequestDto;
import kdu.cse.unispace.requestdto.member.MemberUpdateRequestDto;
import kdu.cse.unispace.responsedto.BasicResponse;
import kdu.cse.unispace.responsedto.member.*;
import kdu.cse.unispace.responsedto.space.MemberSpaceDto;
import kdu.cse.unispace.responsedto.team.TeamSearchByNameDto;
import kdu.cse.unispace.service.MemberService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private static final int SUCCESS = 200;
    private static final int CREATED = 201;

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder; //비밀번호 암호화
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @PostMapping("/member") //회원가입
    public ResponseEntity<JoinMemberResponse> joinMember(@Validated @RequestBody MemberJoinRequestDto memberJoinRequestDto) {
        String encodedPassword = passwordEncoder.encode(memberJoinRequestDto.getPassword()); // 비밀번호 암호화
        memberJoinRequestDto.setPassword(encodedPassword);
        Long memberId = memberService.join(memberJoinRequestDto);
        JoinMemberResponse joinMemberResponse = new JoinMemberResponse(memberId, CREATED, "회원가입 완료");
        return new ResponseEntity<>(joinMemberResponse, HttpStatus.CREATED);
    }

    @GetMapping("/member/{memberId}") //회원 단건 조회
    public ResponseEntity<BasicResponse> getMember(@PathVariable("memberId") Long memberId) {
        Member member = memberService.findOne(memberId);
        GetMemberResponseDto getMemberResponseDto = new GetMemberResponseDto(member);
        BasicResponse basicResponse = new BasicResponse<>(1, "회원 조회 성공", getMemberResponseDto);
        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
    }

    @GetMapping("/member/name") //이름으로 회원 조회
    public ResponseEntity<BasicResponse> getMembersByName(@RequestParam String memberName,
                                                          @RequestParam(defaultValue = "10") int limit) {
        List<MemberSearchByNameDto> getMemberResponseDto = memberService.searchMembersByName(memberName, limit);

        BasicResponse basicResponse = new BasicResponse<>(1, "회원 조회 성공", getMemberResponseDto);
        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
    }

    @GetMapping("/member/{memberId}/space") //회원 스페이스 조회
    @PreAuthorize("@jwtAuthenticationFilter.isMemberOwner(#request, #memberId)")
    public ResponseEntity<BasicResponse> getMemberSpace(@PathVariable("memberId") Long memberId, HttpServletRequest request) {
        Member member = memberService.findOne(memberId);
        MemberSpaceDto memberSpaceDto = new MemberSpaceDto(member);
        BasicResponse basicResponse = new BasicResponse<>(1, "스페이스 조회 성공", memberSpaceDto);
        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
    }

    @DeleteMapping("/member/{memberId}") // 회원 삭제
    @PreAuthorize("@jwtAuthenticationFilter.isMemberOwner(#request, #memberId)")
    public ResponseEntity<MemberBasicResponse> deleteMember(@PathVariable Long memberId, HttpServletRequest request) {
        memberService.delete(memberId);
        MemberBasicResponse memberBasicResponse = new MemberBasicResponse(SUCCESS, "회원 삭제가 정상적으로 되었습니다.");
        return new ResponseEntity<>(memberBasicResponse, HttpStatus.OK);

    }

    @PatchMapping("/member/{memberId}") // 회원 업데이트
    @PreAuthorize("@jwtAuthenticationFilter.isMemberOwner(#request, #memberId)")
    public ResponseEntity<UpdateMemberResponse> updateMember(@PathVariable Long memberId,
                                                             @RequestBody MemberUpdateRequestDto memberUpdateRequestDto,
                                                             HttpServletRequest request) {
        Long updatedMemberId = memberService.update(memberId, memberUpdateRequestDto);
        UpdateMemberResponse updateMemberResponse = new UpdateMemberResponse(updatedMemberId, SUCCESS, "멤버 업데이트 완료");
        return new ResponseEntity<>(updateMemberResponse, HttpStatus.OK);
    }

    @GetMapping("/member") //로그인한 회원 조회 api
    public ResponseEntity<BasicResponse> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Member member = memberService.findByEmail(email);

        GetMemberResponseDto getMemberResponseDto = new GetMemberResponseDto(member);
        BasicResponse basicResponse = new BasicResponse<>(1, "회원 조회 성공",  getMemberResponseDto);
        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
    }

}
