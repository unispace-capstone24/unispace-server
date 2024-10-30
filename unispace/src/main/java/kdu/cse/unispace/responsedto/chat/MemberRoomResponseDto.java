package kdu.cse.unispace.responsedto.chat;

import kdu.cse.unispace.domain.chat.Room;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MemberRoomResponseDto { //회원이 들어가있는 채팅방을 보여주는 DTO

    private Long chatRoomId;
    private String roomName;
    private String type;
    private Long id; //팀 또는 상대방 id
    private List<String> memberName = new ArrayList<>();

    public MemberRoomResponseDto(Room room, Long memberId) {
        this.chatRoomId = room.getId();
        this.roomName = room.getRoomName();
        this.memberName = room.getMemberNames();

        if (room.getSpace() == null) {
            type = "개인 채팅방";
            if(memberId.equals(room.getFriendId())){
                //친구쪽에서 생성된 채팅방이지만 조회할때
                this.id = room.getMember1().getId();
            }else{
                this.id = room.getFriendId();
            }


        } else {
            type = "팀 채팅방";
            this.id = room.getTeamId();
        }
    }
}