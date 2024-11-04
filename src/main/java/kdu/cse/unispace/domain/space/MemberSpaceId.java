package kdu.cse.unispace.domain.space;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Embeddable
@Getter
@Setter
public class MemberSpaceId implements Serializable {

    private Long memberId;

    private Long spaceId;
}