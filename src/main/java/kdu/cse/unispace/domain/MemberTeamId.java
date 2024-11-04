package kdu.cse.unispace.domain;


import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberTeamId implements Serializable { //외래키만으로 복합키를 사용하기 위한 클래스

    private Long memberId;
    private Long teamId;

    public MemberTeamId(Long memberId, Long teamId) {
        this.memberId = memberId;
        this.teamId = teamId;
    }
}
