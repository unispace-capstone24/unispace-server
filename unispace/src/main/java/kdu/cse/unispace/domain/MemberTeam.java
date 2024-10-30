package kdu.cse.unispace.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberTeam {  //회원과 팀 사이의 엔티티


    @EmbeddedId
    private MemberTeamId id; //외래키를 복합키로

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("memberId") //fk를 pk로
    @JoinColumn(name = "member_id")
    private Member member;


    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("teamId") //fk를 pk로
    @JoinColumn(name = "team_id")
    private Team team;

    private String teamName;

    //필요하면 양방향..
//    @OneToMany(mappedBy = "team")
//    private List<MemberTeam> members = new ArrayList<>();
//
//    @OneToOne(mappedBy = "team", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private TeamSpace space;

//    public void addMember(Member member) {
//        MemberTeam memberTeam = new MemberTeam();
//        memberTeam.setTeam(this);
//        memberTeam.setMember(member);
//        members.add(memberTeam);
//        member.getTeams().add(memberTeam);
//    }

    public MemberTeam(Member member, Team team) {  //생성자

        this.id = new MemberTeamId(member.getId(), team.getId());

        this.member = member;
        this.team = team;

        this.teamName = team.getTeamName();
    }
}
