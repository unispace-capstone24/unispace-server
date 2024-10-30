package kdu.cse.unispace.domain.friend;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import kdu.cse.unispace.domain.Member;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FriendShip {

    @Id
    @GeneratedValue
    @Column(name = "friendship_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_id")
    private Member friend;

    @Enumerated(EnumType.STRING)
    private FriendStatus status;

    public FriendShip(Member member, Member friend) {
        this.member = member;
        this.friend = friend;
    }

    public void setStatus(FriendStatus accepted) {
        this.status = accepted;
    }
}
