package kdu.cse.unispace.responsedto.team;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTeamResponse {
    private Long teamId;
    private Integer status;
    private String message;
}
