package kdu.cse.unispace.responsedto.member;

import kdu.cse.unispace.domain.Member;
import kdu.cse.unispace.domain.friend.FriendStatus;
import kdu.cse.unispace.responsedto.friend.FriendDto;
import kdu.cse.unispace.responsedto.friend.FriendListDto;
import kdu.cse.unispace.responsedto.team.TeamListDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class GetMemberResponseDto {

    private Long id;
    private Long spaceId;
    private String email;
    private String memberName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean status;

//    private MemberSpaceDto memberSpaceDto;

    private int teamCount; //가입되어있는 팀의 갯수
    private List<TeamListDto> teamList;

    private FriendListDto friendList;

    //private List<FriendDto> friendList = new ArrayList<>();




    public GetMemberResponseDto(Member member) {
        this.id = member.getId();
        this.spaceId = member.getMemberSpace().getId();
        this.email = member.getEmail();
        this.memberName = member.getMemberName();
        this.createdAt = member.getCreatedAt();
        this.updatedAt = member.getUpdatedAt();
        this.status = member.getStatus();

//        //스페이스 관련
//        this.memberSpace = member.getMemberSpace();

        // 회원의 입장에서 팀들의 정보 담기
        if (member.getMemberTeams() != null) {
            teamList = member.getMemberTeams().stream()
                    .map(memberTeam -> new TeamListDto(memberTeam))
                    .collect(Collectors.toList());
            teamCount = teamList.size();
        }


        // 친구요청을 건 상대  && status가 accepted인 경우 friendList에 저장
        friendList = new FriendListDto(
                member.getFriendRequester().stream()
                        .filter(friendShip -> friendShip.getStatus() == FriendStatus.ACCEPTED)
                        .map(friendShip -> new FriendDto(friendShip.getFriend()))
                        .collect(Collectors.toList()));
    }

}
