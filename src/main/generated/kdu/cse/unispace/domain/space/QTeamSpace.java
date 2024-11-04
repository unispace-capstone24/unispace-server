package kdu.cse.unispace.domain.space;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTeamSpace is a Querydsl query type for TeamSpace
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTeamSpace extends EntityPathBase<TeamSpace> {

    private static final long serialVersionUID = -721330532L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTeamSpace teamSpace = new QTeamSpace("teamSpace");

    public final QSpace _super;

    //inherited
    public final NumberPath<Long> id;

    //inherited
    public final ListPath<Page, QPage> pageList;

    // inherited
    public final kdu.cse.unispace.domain.space.schedule.QSchedule schedule;

    public final kdu.cse.unispace.domain.QTeam team;

    public QTeamSpace(String variable) {
        this(TeamSpace.class, forVariable(variable), INITS);
    }

    public QTeamSpace(Path<? extends TeamSpace> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTeamSpace(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTeamSpace(PathMetadata metadata, PathInits inits) {
        this(TeamSpace.class, metadata, inits);
    }

    public QTeamSpace(Class<? extends TeamSpace> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QSpace(type, metadata, inits);
        this.id = _super.id;
        this.pageList = _super.pageList;
        this.schedule = _super.schedule;
        this.team = inits.isInitialized("team") ? new kdu.cse.unispace.domain.QTeam(forProperty("team"), inits.get("team")) : null;
    }

}

