package kdu.cse.unispace.controller;

import kdu.cse.unispace.domain.Member;
import kdu.cse.unispace.requestdto.member.MemberJoinRequestDto;
import kdu.cse.unispace.requestdto.member.MemberUpdateRequestDto;
import kdu.cse.unispace.responsedto.BasicResponse;
import kdu.cse.unispace.responsedto.member.*;
import kdu.cse.unispace.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private static final int SUCCESS = 200;
    private static final int CREATED = 201;

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder; // 비밀번호 암호화

    private Member getAuthenticatedMember(HttpSession session) {
        Long memberId = (Long) session.getAttribute("memberId");
        if (memberId == null) {
            throw new IllegalStateException("사용자가 인증되지 않았습니다.");
        }
        return memberService.findOne(memberId);
    }

    @PostMapping("/member") // 회원가입
    public ResponseEntity<JoinMemberResponse> joinMember(@Validated @RequestBody MemberJoinRequestDto memberJoinRequestDto) {
        String encodedPassword = passwordEncoder.encode(memberJoinRequestDto.getPassword()); // 비밀번호 암호화
        memberJoinRequestDto.setPassword(encodedPassword);
        Long memberId = memberService.join(memberJoinRequestDto);
        JoinMemberResponse joinMemberResponse = new JoinMemberResponse(memberId, CREATED, "회원가입 완료");
        return new ResponseEntity<>(joinMemberResponse, HttpStatus.CREATED);
    }

    @PostMapping("/login") // 로그인
    public ResponseEntity<BasicResponse> login(@RequestParam String email, @RequestParam String password, HttpSession session) {
        Member member = memberService.findByEmail(email);
        if (member == null || !passwordEncoder.matches(password, member.getPassword())) {
            return new ResponseEntity<>(new BasicResponse<>(0, "이메일 또는 비밀번호가 잘못되었습니다.", null), HttpStatus.UNAUTHORIZED);
        }
        session.setAttribute("memberId", member.getId());
        return new ResponseEntity<>(new BasicResponse<>(1, "로그인 성공", null), HttpStatus.OK);
    }

    @PostMapping("/logout") // 로그아웃
    public ResponseEntity<BasicResponse> logout(HttpSession session) {
        session.invalidate();
        return new ResponseEntity<>(new BasicResponse<>(1, "로그아웃 성공", null), HttpStatus.OK);
    }

    @GetMapping("/member/{memberId}") // 회원 단건 조회
    public ResponseEntity<BasicResponse> getMember(@PathVariable("memberId") Long memberId) {
        Member member = memberService.findOne(memberId);
        GetMemberResponseDto getMemberResponseDto = new GetMemberResponseDto(member);
        BasicResponse basicResponse = new BasicResponse<>(1, "회원 조회 성공", getMemberResponseDto);
        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
    }

    @DeleteMapping("/member") // 회원 삭제
    public ResponseEntity<MemberBasicResponse> deleteMember(HttpSession session) {
        Member member = getAuthenticatedMember(session);
        memberService.delete(member.getId());
        session.invalidate(); // 회원 삭제 후 로그아웃 처리
        return new ResponseEntity<>(new MemberBasicResponse(SUCCESS, "회원 삭제가 정상적으로 되었습니다."), HttpStatus.OK);
    }

    @PatchMapping("/member") // 회원 업데이트
    public ResponseEntity<UpdateMemberResponse> updateMember(@RequestBody MemberUpdateRequestDto memberUpdateRequestDto,
                                                             HttpSession session) {
        Member member = getAuthenticatedMember(session);
        Long updatedMemberId = memberService.update(member.getId(), memberUpdateRequestDto);
        UpdateMemberResponse updateMemberResponse = new UpdateMemberResponse(updatedMemberId, SUCCESS, "멤버 업데이트 완료");
        return new ResponseEntity<>(updateMemberResponse, HttpStatus.OK);
    }

    @GetMapping("/member") // 현재 로그인된 회원 조회
    public ResponseEntity<BasicResponse> getCurrentUser(HttpSession session) {
        Member member = getAuthenticatedMember(session);
        GetMemberResponseDto getMemberResponseDto = new GetMemberResponseDto(member);
        BasicResponse basicResponse = new BasicResponse<>(1, "회원 조회 성공", getMemberResponseDto);
        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
    }
}
