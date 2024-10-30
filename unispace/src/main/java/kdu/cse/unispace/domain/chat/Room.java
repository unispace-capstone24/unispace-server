package kdu.cse.unispace.domain.chat;

import jakarta.persistence.*;
import kdu.cse.unispace.domain.Member;
import kdu.cse.unispace.domain.MemberTeam;
import kdu.cse.unispace.domain.space.Space;
import kdu.cse.unispace.domain.space.TeamSpace;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room {   //채팅방
    @Id
    @GeneratedValue
    @Column(name = "room_id")
    private Long id;

    Long teamId;
    Long friendId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "space_id")
    private Space space;

    @OneToMany(mappedBy = "room", cascade = CascadeType.REMOVE) //채팅내역
    private List<Message> messageList = new ArrayList<>();

    private String roomName; //채팅방 이름

    //친구와의 채팅방시 사용
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_1")
    private Member member1; //참여자 1

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_2")
    private Member member2; //참여자 2

    public Room(String roomName, Space space) { //팀에서 채팅방 생성시
        this.roomName = roomName;
        this.space = space;
        TeamSpace teamSpace = (TeamSpace) space;
        this.teamId = teamSpace.getTeam().getId();
        space.getRoomList().add(this);
    }
//    public Room(Space space, String roomName, Long memberId, Long friendId) { //개인채팅방 생성시
//        this.roomName = roomName;
//        this.space = space;
//        this.memberId = memberId;
//        this.friendId = friendId;
//    }

    public Room(String roomName, Member member, Member friend) {
        this.roomName = roomName;
        this.member1 = member;
        this.member2 = friend;
        this.friendId = friend.getId();
        //member.getRoomList1().add(this);
        //friend.getRoomList2().add(this);
    }

    public List<String> getMemberNames() {
        List<String> memberNames = new ArrayList<>();
        if (member1 != null) {
            memberNames.add(member1.getMemberName());
        }
        if (member2 != null) {
            memberNames.add(member2.getMemberName());
        }
        if (space != null && space instanceof TeamSpace) {
            TeamSpace teamSpace = (TeamSpace) space;
            List<MemberTeam> memberTeams = teamSpace.getTeam().getMemberTeams();
            List<String> teamMemberNames = memberTeams.stream()
                    .map(MemberTeam::getMember)
                    .map(Member::getMemberName)
                    .collect(Collectors.toList());
            memberNames.addAll(teamMemberNames);
        }
        return memberNames;
    }

    //public void setFriendRoomId(Long friendRoomId) {
//        this.friendRoomId=friendRoomId;
//    }


//    public void roomSpaceChange(Space space) {
//
//    }

}
