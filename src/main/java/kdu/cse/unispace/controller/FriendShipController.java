package kdu.cse.unispace.controller;

import kdu.cse.unispace.domain.Member;
import kdu.cse.unispace.domain.friend.FriendShip;
import kdu.cse.unispace.domain.friend.FriendStatus;
import kdu.cse.unispace.exception.friend.FriendAddException;
import kdu.cse.unispace.requestdto.friendship.FriendRequestDto;
import kdu.cse.unispace.responsedto.BasicResponse;
import kdu.cse.unispace.responsedto.friend.FriendBasicResponse;
import kdu.cse.unispace.responsedto.friend.FriendDto;
import kdu.cse.unispace.responsedto.friend.SendFriendShipResponseDto;
import kdu.cse.unispace.service.FriendShipService;
import kdu.cse.unispace.service.MemberService;
import kdu.cse.unispace.service.RoomService;
import kdu.cse.unispace.service.ScheduleService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class FriendShipController {

    private static final int SUCCESS = 200;
    private static final int CREATED = 201;

    private final FriendShipService friendShipService;
    private final MemberService memberService;
    private final ScheduleService scheduleService;
    private final RoomService roomService;

    private Member getAuthenticatedMember(HttpSession session) {
        Long memberId = (Long) session.getAttribute("memberId");
        if (memberId == null) {
            throw new IllegalStateException("사용자가 인증되지 않았습니다.");
        }
        return memberService.findOne(memberId);
    }

    @GetMapping("/friend")
    public ResponseEntity<BasicResponse> friend(HttpSession session) {
        Member member = getAuthenticatedMember(session);
        List<Member> friendList = friendShipService.findFriendList(member);
        List<FriendDto> collect = friendList.stream()
                .map(FriendDto::new)
                .collect(Collectors.toList());
        BasicResponse basicResponse = new BasicResponse<>(collect.size(), "친구목록 요청 성공", collect);
        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
    }

    @PostMapping("/friend")
    public ResponseEntity<SendFriendShipResponseDto> sendFriendShip(@RequestBody FriendRequestDto friendRequestDto,
                                                                    HttpSession session) {
        Member friendRequester = getAuthenticatedMember(session);
        Member friendReceiver = memberService.findOne(friendRequestDto.getFriendId());

        for (FriendShip requester : friendRequester.getFriendRequester()) {
            if (Objects.equals(requester.getFriend().getId(), friendReceiver.getId())) {
                if (requester.getStatus().equals(FriendStatus.ACCEPTED)) {
                    throw new FriendAddException("이미 친구관계를 맺은 회원입니다.");
                } else {
                    throw new FriendAddException("이미 친구신청을 보냈습니다.");
                }
            }
        }

        FriendShip friendShip = new FriendShip(friendRequester, friendReceiver);
        friendShipService.addFriend(friendShip);

        SendFriendShipResponseDto responseDto =
                new SendFriendShipResponseDto(friendRequester.getId(), CREATED, "친구신청을 보냈습니다.");
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/friend/{friendId}")
    public ResponseEntity<FriendBasicResponse> deleteFriendShip(@PathVariable("friendId") Long friendId,
                                                                HttpSession session) {
        Member member = getAuthenticatedMember(session);
        friendShipService.deleteFriendShip(member.getId(), friendId);
        roomService.removeFriendRoom(member.getId(), friendId); // 친구 삭제 시 채팅방도 삭제

        return new ResponseEntity<>(new FriendBasicResponse(SUCCESS, "친구 삭제가 정상적으로 되었습니다."), HttpStatus.OK);
    }

    @GetMapping("/friendReceive")
    public ResponseEntity<BasicResponse> friendReceiveList(HttpSession session) {
        Member member = getAuthenticatedMember(session);
        List<Member> friendReceiveList = friendShipService.findFriendReceiveList(member.getId());
        List<FriendDto> collect = friendReceiveList.stream()
                .map(FriendDto::new)
                .collect(Collectors.toList());
        BasicResponse basicResponse = new BasicResponse<>(collect.size(), "친구요청목록 요청 성공", collect);
        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
    }

    @PostMapping("/friend/reject")
    public ResponseEntity<FriendBasicResponse> friendRequestReject(@RequestBody FriendRequestDto friendRequestDto,
                                                                   HttpSession session) {
        Member member = getAuthenticatedMember(session);
        friendShipService.rejectFriendShip(member.getId(), friendRequestDto.getFriendId());
        return new ResponseEntity<>(new FriendBasicResponse(SUCCESS, "친구 신청을 거절했습니다."), HttpStatus.OK);
    }
}
