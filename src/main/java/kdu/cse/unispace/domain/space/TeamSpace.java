package kdu.cse.unispace.domain.space;

import jakarta.persistence.*;
import kdu.cse.unispace.domain.Team;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamSpace extends Space {


    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "team_id")
    private Team team;



    public TeamSpace(Team team) {
        this.team = team;
    }
}