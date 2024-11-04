package kdu.cse.unispace.controller;

import kdu.cse.unispace.config.jwt.JwtAuthenticationFilter;
import kdu.cse.unispace.domain.Member;
import kdu.cse.unispace.domain.Team;
import kdu.cse.unispace.domain.chat.Room;
import kdu.cse.unispace.domain.space.Space;
import kdu.cse.unispace.exception.RequiredValueMissingException;
import kdu.cse.unispace.requestdto.chat.CreateMessageRequestDto;
import kdu.cse.unispace.requestdto.chat.CreateRoomRequestDto;
import kdu.cse.unispace.responsedto.BasicResponse;
import kdu.cse.unispace.responsedto.chat.*;
import kdu.cse.unispace.service.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class RoomController {
    private static final int SUCCESS = 200;
    private static final int CREATED = 201;
    private final RoomService roomService;
    private final MemberService memberService;
    private final TeamService teamService;
    private final SpaceService spaceService;
    private final MessageService messageService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;


    @GetMapping("/chat/room/{roomId}") //채팅방 조회
    @PreAuthorize("@jwtAuthenticationFilter.isRoomOwner(#request, #roomId)")
    public ResponseEntity<BasicResponse> getRoom(@PathVariable("roomId") Long roomId, HttpServletRequest request) {
        Room room = roomService.findOne(roomId);
        GetRoomResponseDto roomResponseDto = new GetRoomResponseDto(room);
        BasicResponse basicResponse = new BasicResponse<>(1, "채팅방 조회 성공", roomResponseDto);
        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
    }

    @GetMapping("/member/{memberId}/chatrooms") //회원이 들어가있는 채팅방 조회
    @PreAuthorize("@jwtAuthenticationFilter.isMemberOwner(#request, #memberId)")
    public ResponseEntity<BasicResponse> getMemberChatting(@PathVariable("memberId") Long memberId,
                                                                         HttpServletRequest request) {
        List<MemberRoomResponseDto> membersRoom = roomService.findMembersRoom(memberId);
        BasicResponse basicResponse = new BasicResponse<>(1, "채팅방 조회 성공", membersRoom);
        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
    }

//    @PostMapping("/chat/room/{teamId}/team")// 팀 채팅방 생성
//    @PreAuthorize("@jwtAuthenticationFilter.isInTeam(#request, #teamId)")
//    public ResponseEntity<CreateRoomResponse> createChattingRoom(@PathVariable("teamId") Long teamId,
//                                                                 @RequestBody CreateRoomRequestDto roomRequestDto,
//                                                                 HttpServletRequest request) {
//        Team team = teamService.findOne(teamId);
//        Space space = team.getTeamSpace();
//        Long roomId = roomService.makeTeamChattingRoom(space, roomRequestDto.getRoomName());
//        CreateRoomResponse createRoomResponse = new CreateRoomResponse(roomId, CREATED, "채팅방 생성 완료");
//        return new ResponseEntity<>(createRoomResponse, HttpStatus.CREATED);
//    }
//
//    @DeleteMapping("/chat/room/{roomId}") //채팅방 제거
//    @PreAuthorize("@jwtAuthenticationFilter.isRoomOwner(#request, #roomId)")
//    public ResponseEntity<BasicResponse> removeRoom(@PathVariable("roomId") Long roomId,
//                                                    HttpServletRequest request) {
//        roomService.removeRoom(roomId);
//        BasicResponse basicResponse = new BasicResponse<>(1, "채팅방 제거 성공", null);
//        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
//    }


//    @PostMapping("/chat/{roomId}/message") //메세지 보냄
//    @PreAuthorize("@jwtAuthenticationFilter.isRoomOwner(#request, #roomId)")
//    public ResponseEntity<CreateMessageResponse> createMessage(@PathVariable("roomId") Long roomId,
//                                                               @RequestBody CreateMessageRequestDto messageRequestDto,
//                                                               HttpServletRequest request) {
//
//        Room room = roomService.findOne(roomId);
//        Member member = memberService.findOne(jwtAuthenticationFilter.findMemberByUUID(request).getId());
//        Long messageId = messageService.makeMessage(member, room, messageRequestDto.getContent());
//
//        CreateMessageResponse createMessageResponse = new CreateMessageResponse(messageId, CREATED, "메세지 전송 완료");
//        return new ResponseEntity<>(createMessageResponse, HttpStatus.CREATED);
//    }


}
