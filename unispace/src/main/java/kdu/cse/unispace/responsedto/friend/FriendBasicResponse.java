package kdu.cse.unispace.responsedto.friend;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FriendBasicResponse {
    private Integer status;
    private String message;
}
