package kdu.cse.unispace.responsedto.friend;

import lombok.Data;

import java.util.List;

@Data
public class FriendListDto {

    private int friendCount;
    private List<FriendDto> friendList;

    public FriendListDto(List<FriendDto> friendList) {
        this.friendList = friendList;
        this.friendCount = friendList.size();
    }

}
