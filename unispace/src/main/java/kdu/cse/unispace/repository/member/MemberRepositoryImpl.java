package kdu.cse.unispace.repository.member;

import com.querydsl.core.types.EntityPath;
import com.querydsl.jpa.impl.JPAQuery;
import kdu.cse.unispace.domain.Member;
import kdu.cse.unispace.domain.QMember;
import kdu.cse.unispace.domain.Team;
import org.apache.tomcat.Jar;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class MemberRepositoryImpl  extends QuerydslRepositorySupport implements MemberRepositoryCustom{

    public MemberRepositoryImpl() {
        super(Member.class);
    }

    @Override
    public List<Member> searchMembersByName(String query, int limit) {
        QMember member = QMember.member;


        JPAQuery<Member> jpaQuery = new JPAQuery<>();
        List<Member> results = jpaQuery.from(member)
                .orderBy(member.memberName.asc())
                .fetch();

        return results;
    }

}
