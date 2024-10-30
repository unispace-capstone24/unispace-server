package kdu.cse.unispace.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMemberTeamId is a Querydsl query type for MemberTeamId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QMemberTeamId extends BeanPath<MemberTeamId> {

    private static final long serialVersionUID = 1605163159L;

    public static final QMemberTeamId memberTeamId = new QMemberTeamId("memberTeamId");

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public final NumberPath<Long> teamId = createNumber("teamId", Long.class);

    public QMemberTeamId(String variable) {
        super(MemberTeamId.class, forVariable(variable));
    }

    public QMemberTeamId(Path<? extends MemberTeamId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMemberTeamId(PathMetadata metadata) {
        super(MemberTeamId.class, metadata);
    }

}
