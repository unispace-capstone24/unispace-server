package kdu.cse.unispace.domain.space;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberSpace is a Querydsl query type for MemberSpace
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberSpace extends EntityPathBase<MemberSpace> {

    private static final long serialVersionUID = -1085930561L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberSpace memberSpace = new QMemberSpace("memberSpace");

    public final QSpace _super;

    //inherited
    public final NumberPath<Long> id;

    public final kdu.cse.unispace.domain.QMember member;

    //inherited
    public final ListPath<Page, QPage> pageList;

    // inherited
    public final kdu.cse.unispace.domain.space.schedule.QSchedule schedule;

    public QMemberSpace(String variable) {
        this(MemberSpace.class, forVariable(variable), INITS);
    }

    public QMemberSpace(Path<? extends MemberSpace> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberSpace(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberSpace(PathMetadata metadata, PathInits inits) {
        this(MemberSpace.class, metadata, inits);
    }

    public QMemberSpace(Class<? extends MemberSpace> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QSpace(type, metadata, inits);
        this.id = _super.id;
        this.member = inits.isInitialized("member") ? new kdu.cse.unispace.domain.QMember(forProperty("member"), inits.get("member")) : null;
        this.pageList = _super.pageList;
        this.schedule = _super.schedule;
    }

}

