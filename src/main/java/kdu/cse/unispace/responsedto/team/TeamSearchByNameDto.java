package kdu.cse.unispace.responsedto.team;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class TeamSearchByNameDto {
    private Long teamId;
    private String teamName;
}
