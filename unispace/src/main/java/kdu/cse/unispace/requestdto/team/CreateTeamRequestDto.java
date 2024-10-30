package kdu.cse.unispace.requestdto.team;

import kdu.cse.unispace.validation.team.UniqueTeamName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTeamRequestDto {
    private Long memberId;

    @UniqueTeamName
    private String teamName;
}
