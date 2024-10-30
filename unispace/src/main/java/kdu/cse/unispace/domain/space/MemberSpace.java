package kdu.cse.unispace.domain.space;


import jakarta.persistence.*;
import kdu.cse.unispace.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberSpace extends Space {


    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;


    public MemberSpace(Member member) {
        this.member = member;
    }

}


