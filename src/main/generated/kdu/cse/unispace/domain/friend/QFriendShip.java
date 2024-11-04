package kdu.cse.unispace.domain.friend;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFriendShip is a Querydsl query type for FriendShip
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFriendShip extends EntityPathBase<FriendShip> {

    private static final long serialVersionUID = 409706917L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFriendShip friendShip = new QFriendShip("friendShip");

    public final kdu.cse.unispace.domain.QMember friend;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final kdu.cse.unispace.domain.QMember member;

    public final EnumPath<FriendStatus> status = createEnum("status", FriendStatus.class);

    public QFriendShip(String variable) {
        this(FriendShip.class, forVariable(variable), INITS);
    }

    public QFriendShip(Path<? extends FriendShip> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFriendShip(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFriendShip(PathMetadata metadata, PathInits inits) {
        this(FriendShip.class, metadata, inits);
    }

    public QFriendShip(Class<? extends FriendShip> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.friend = inits.isInitialized("friend") ? new kdu.cse.unispace.domain.QMember(forProperty("friend"), inits.get("friend")) : null;
        this.member = inits.isInitialized("member") ? new kdu.cse.unispace.domain.QMember(forProperty("member"), inits.get("member")) : null;
    }

}

