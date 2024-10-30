package kdu.cse.unispace.responsedto.team;

import kdu.cse.unispace.domain.MemberTeam;
import kdu.cse.unispace.domain.Team;
import kdu.cse.unispace.responsedto.memberTeam.MemberTeamResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class TeamListDto {
    private Long teamId;
    private int memberCount;
    private String teamName;
    private List<MemberTeamResponseDto> memberTeamList;

    public TeamListDto(MemberTeam memberTeam) {
        Team team = memberTeam.getTeam();
        this.teamId = team.getId();
        this.teamName = memberTeam.getTeamName();
        this.memberCount = team.getMemberCount();

        memberTeamList = memberTeam.getTeam().getMemberTeams().stream()
                .map(MemberTeamResponseDto::new)
                .collect(Collectors.toList());
    }
    public TeamListDto(Team team) {
        this(team.getId(), team.getMemberCount(), team.getTeamName(), team.getMemberTeams().stream()
                .map(MemberTeamResponseDto::new)
                .collect(Collectors.toList()));
    }
}
