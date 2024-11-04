package kdu.cse.unispace.domain.space;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSpace is a Querydsl query type for Space
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSpace extends EntityPathBase<Space> {

    private static final long serialVersionUID = 2076283001L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSpace space = new QSpace("space");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<Page, QPage> pageList = this.<Page, QPage>createList("pageList", Page.class, QPage.class, PathInits.DIRECT2);

    public final kdu.cse.unispace.domain.space.schedule.QSchedule schedule;

    public QSpace(String variable) {
        this(Space.class, forVariable(variable), INITS);
    }

    public QSpace(Path<? extends Space> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSpace(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSpace(PathMetadata metadata, PathInits inits) {
        this(Space.class, metadata, inits);
    }

    public QSpace(Class<? extends Space> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.schedule = inits.isInitialized("schedule") ? new kdu.cse.unispace.domain.space.schedule.QSchedule(forProperty("schedule"), inits.get("schedule")) : null;
    }

}

