package kdu.cse.unispace.domain.space;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMemberSpaceId is a Querydsl query type for MemberSpaceId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QMemberSpaceId extends BeanPath<MemberSpaceId> {

    private static final long serialVersionUID = 97786170L;

    public static final QMemberSpaceId memberSpaceId = new QMemberSpaceId("memberSpaceId");

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public final NumberPath<Long> spaceId = createNumber("spaceId", Long.class);

    public QMemberSpaceId(String variable) {
        super(MemberSpaceId.class, forVariable(variable));
    }

    public QMemberSpaceId(Path<? extends MemberSpaceId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMemberSpaceId(PathMetadata metadata) {
        super(MemberSpaceId.class, metadata);
    }

}

