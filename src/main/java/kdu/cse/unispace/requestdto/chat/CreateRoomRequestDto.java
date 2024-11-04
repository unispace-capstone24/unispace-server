package kdu.cse.unispace.requestdto.chat;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateRoomRequestDto {
    private String roomName;
}
