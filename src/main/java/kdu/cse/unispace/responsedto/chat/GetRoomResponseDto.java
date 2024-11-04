package kdu.cse.unispace.responsedto.chat;

import kdu.cse.unispace.domain.chat.Message;
import kdu.cse.unispace.domain.chat.Room;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class GetRoomResponseDto {
    private String roomName;
    private List<MessageResponseDto> messageList;
    private String type;
    public GetRoomResponseDto(Room room) {
        roomName = room.getRoomName();
        messageList = room.getMessageList().stream()
                .map(MessageResponseDto::new)
                .collect(Collectors.toList());
        if (room.getSpace() == null) {
            type = "개인 채팅방";
        } else {
            type = "팀 채팅방";
        }
    }
}
