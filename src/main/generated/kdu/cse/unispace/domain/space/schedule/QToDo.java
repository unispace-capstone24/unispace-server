package kdu.cse.unispace.domain.space.schedule;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QToDo is a Querydsl query type for ToDo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QToDo extends EntityPathBase<ToDo> {

    private static final long serialVersionUID = 176738832L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QToDo toDo = new QToDo("toDo");

    public final QCategory category;

    public final BooleanPath completed = createBoolean("completed");

    public final DateTimePath<java.time.LocalDateTime> date = createDateTime("date", java.time.LocalDateTime.class);

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QToDo(String variable) {
        this(ToDo.class, forVariable(variable), INITS);
    }

    public QToDo(Path<? extends ToDo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QToDo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QToDo(PathMetadata metadata, PathInits inits) {
        this(ToDo.class, metadata, inits);
    }

    public QToDo(Class<? extends ToDo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new QCategory(forProperty("category"), inits.get("category")) : null;
    }

}

