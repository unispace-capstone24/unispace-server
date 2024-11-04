package kdu.cse.unispace.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 1292502879L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMember member = new QMember("member1");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath email = createString("email");

    public final ListPath<kdu.cse.unispace.domain.friend.FriendShip, kdu.cse.unispace.domain.friend.QFriendShip> friendReceiver = this.<kdu.cse.unispace.domain.friend.FriendShip, kdu.cse.unispace.domain.friend.QFriendShip>createList("friendReceiver", kdu.cse.unispace.domain.friend.FriendShip.class, kdu.cse.unispace.domain.friend.QFriendShip.class, PathInits.DIRECT2);

    public final ListPath<kdu.cse.unispace.domain.friend.FriendShip, kdu.cse.unispace.domain.friend.QFriendShip> friendRequester = this.<kdu.cse.unispace.domain.friend.FriendShip, kdu.cse.unispace.domain.friend.QFriendShip>createList("friendRequester", kdu.cse.unispace.domain.friend.FriendShip.class, kdu.cse.unispace.domain.friend.QFriendShip.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath memberName = createString("memberName");

    public final kdu.cse.unispace.domain.space.QMemberSpace memberSpace;

    public final ListPath<MemberTeam, QMemberTeam> memberTeams = this.<MemberTeam, QMemberTeam>createList("memberTeams", MemberTeam.class, QMemberTeam.class, PathInits.DIRECT2);

    public final StringPath password = createString("password");

    public final BooleanPath status = createBoolean("status");

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public QMember(String variable) {
        this(Member.class, forVariable(variable), INITS);
    }

    public QMember(Path<? extends Member> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMember(PathMetadata metadata, PathInits inits) {
        this(Member.class, metadata, inits);
    }

    public QMember(Class<? extends Member> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.memberSpace = inits.isInitialized("memberSpace") ? new kdu.cse.unispace.domain.space.QMemberSpace(forProperty("memberSpace"), inits.get("memberSpace")) : null;
    }

}

