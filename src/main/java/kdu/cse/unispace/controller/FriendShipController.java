package kdu.cse.unispace.controller;

import kdu.cse.unispace.config.jwt.JwtAuthenticationFilter;
import kdu.cse.unispace.domain.Member;
import kdu.cse.unispace.domain.friend.FriendShip;
import kdu.cse.unispace.domain.friend.FriendStatus;
import kdu.cse.unispace.exception.friend.FriendAddException;
import kdu.cse.unispace.requestdto.friendship.FriendRequestDto;
import kdu.cse.unispace.responsedto.BasicResponse;
import kdu.cse.unispace.responsedto.friend.FriendBasicResponse;
import kdu.cse.unispace.responsedto.friend.FriendDto;
import kdu.cse.unispace.responsedto.friend.SendFriendShipResponseDto;
import kdu.cse.unispace.responsedto.schedule.ScheduleDto;
import kdu.cse.unispace.service.FriendShipService;
import kdu.cse.unispace.service.MemberService;
import kdu.cse.unispace.service.RoomService;
import kdu.cse.unispace.service.ScheduleService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class FriendShipController {

    private static final int SUCCESS = 200;

    private static final int CREATED = 201;


    private final FriendShipService friendShipService;
    private final MemberService memberService;
    private final ScheduleService scheduleService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final RoomService roomService;

    @GetMapping("/{memberId}/friend")
    @PreAuthorize("@jwtAuthenticationFilter.isMemberOwner(#request, #memberId)")
    public ResponseEntity<BasicResponse> friend(@PathVariable("memberId") Long memberId, HttpServletRequest request) {
        Member member = memberService.findOne(memberId);
        List<Member> friendList = friendShipService.findFriendList(member);
        List<FriendDto> collect = friendList.stream()
                .map(f -> new FriendDto(f))
                .collect(Collectors.toList());
        BasicResponse basicResponse = new BasicResponse<>(collect.size(), "친구목록 요청 성공", collect);
        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
    }

    @PostMapping("/{memberId}/friend") //친구신청
    @PreAuthorize("@jwtAuthenticationFilter.isMemberOwner(#request, #memberId)")
    public ResponseEntity<SendFriendShipResponseDto> sendFriendShip(@PathVariable("memberId") Long memberId,
                                                                    @RequestBody FriendRequestDto friendRequestDto,
                                                                    HttpServletRequest request) {
        //친구 요청 보낸 사람
        Member friendRequester = memberService.findOne(memberId);
        //친구 요청 받은 사람
        Member friendReceiver = memberService.findOne(friendRequestDto.getFriendId());

        for (FriendShip requester : friendRequester.getFriendRequester()) {
            if (Objects.equals(requester.getFriend().getId(), friendReceiver.getId())) {
                if (requester.getStatus().equals(FriendStatus.ACCEPTED)) //친구신청 목록중에 이미 친구상태이면
                    throw new FriendAddException("이미 친구관계를 맺은 회원입니다.");
                else
                    throw new FriendAddException("이미 친구신청을 보냈습니다."); // 친구신청 목록중에 이미 보냈다면
            }
        }

        FriendShip friendShip = new FriendShip(friendRequester, friendReceiver);

        friendShipService.addFriend(friendShip);

        SendFriendShipResponseDto friendResponseDto
                = new SendFriendShipResponseDto(friendRequester.getId(), CREATED, "친구신청을 보냈습니다.");
        return new ResponseEntity<>(friendResponseDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/friend/{memberId}/{friendId}")
    @PreAuthorize("@jwtAuthenticationFilter.isMemberOwner(#request, #memberId)")
    public ResponseEntity<FriendBasicResponse> deleteFriendShip(@PathVariable("memberId") Long memberId,
                                                                @PathVariable("friendId") Long friendId,
                                                                HttpServletRequest request) {
        friendShipService.deleteFriendShip(memberId, friendId);

        roomService.removeFriendRoom(memberId, friendId); //친구 삭제시 채팅방도 삭제

        return new ResponseEntity<>(new FriendBasicResponse(SUCCESS, "친구 삭제가 정상적으로 되었습니다."), HttpStatus.OK);
    }

    @GetMapping("/{memberId}/friendReceive")
    @PreAuthorize("@jwtAuthenticationFilter.isMemberOwner(#request, #memberId)")
    public ResponseEntity<BasicResponse> friendReceiveList(@PathVariable("memberId") Long memberId, HttpServletRequest request) {
        Member member = memberService.findOne(memberId);
        List<Member> friendReceiveList = friendShipService.findFriendReceiveList(member.getId());
        List<FriendDto> collect = friendReceiveList.stream()
                .map(f -> new FriendDto(f))
                .collect(Collectors.toList());
        BasicResponse basicResponse = new BasicResponse<>(collect.size(), "친구요청목록 요청 성공", collect);
        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
    }

    @PostMapping("/{memberId}/friend/reject")
    @PreAuthorize("@jwtAuthenticationFilter.isMemberOwner(#request, #memberId)")
    public ResponseEntity<FriendBasicResponse> friendRequestReject(@PathVariable("memberId") Long memberId, @RequestBody FriendRequestDto friendRequestDto,
                                                                   HttpServletRequest request) {
        Member member = memberService.findOne(memberId);
        friendShipService.rejectFriendShip(member.getId(), friendRequestDto.getFriendId());
        return new ResponseEntity<>(new FriendBasicResponse(SUCCESS, "친구 신청을 거절했습니다."), HttpStatus.OK);
    }
}
