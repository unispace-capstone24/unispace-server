package kdu.cse.unispace.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTeam is a Querydsl query type for Team
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTeam extends EntityPathBase<Team> {

    private static final long serialVersionUID = 743451746L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTeam team = new QTeam("team");

    public final NumberPath<Long> host = createNumber("host", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> memberCount = createNumber("memberCount", Integer.class);

    public final ListPath<MemberTeam, QMemberTeam> memberTeams = this.<MemberTeam, QMemberTeam>createList("memberTeams", MemberTeam.class, QMemberTeam.class, PathInits.DIRECT2);

    public final StringPath teamName = createString("teamName");

    public final kdu.cse.unispace.domain.space.QTeamSpace teamSpace;

    public QTeam(String variable) {
        this(Team.class, forVariable(variable), INITS);
    }

    public QTeam(Path<? extends Team> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTeam(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTeam(PathMetadata metadata, PathInits inits) {
        this(Team.class, metadata, inits);
    }

    public QTeam(Class<? extends Team> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.teamSpace = inits.isInitialized("teamSpace") ? new kdu.cse.unispace.domain.space.QTeamSpace(forProperty("teamSpace"), inits.get("teamSpace")) : null;
    }

}

