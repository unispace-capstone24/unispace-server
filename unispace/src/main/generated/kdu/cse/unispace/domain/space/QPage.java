package kdu.cse.unispace.domain.space;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPage is a Querydsl query type for Page
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPage extends EntityPathBase<Page> {

    private static final long serialVersionUID = 343967932L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPage page = new QPage("page");

    public final ListPath<Block, QBlock> blockList = this.<Block, QBlock>createList("blockList", Block.class, QBlock.class, PathInits.DIRECT2);

    public final ListPath<Page, QPage> childPages = this.<Page, QPage>createList("childPages", Page.class, QPage.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> parentId = createNumber("parentId", Long.class);

    public final QPage parentPage;

    public final QSpace space;

    public final StringPath title = createString("title");

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public QPage(String variable) {
        this(Page.class, forVariable(variable), INITS);
    }

    public QPage(Path<? extends Page> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPage(PathMetadata metadata, PathInits inits) {
        this(Page.class, metadata, inits);
    }

    public QPage(Class<? extends Page> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.parentPage = inits.isInitialized("parentPage") ? new QPage(forProperty("parentPage"), inits.get("parentPage")) : null;
        this.space = inits.isInitialized("space") ? new QSpace(forProperty("space"), inits.get("space")) : null;
    }

}

