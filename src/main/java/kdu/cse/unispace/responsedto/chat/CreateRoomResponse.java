package kdu.cse.unispace.responsedto.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRoomResponse {
    private Long roomId;
    private Integer status;
    private String message;
}
