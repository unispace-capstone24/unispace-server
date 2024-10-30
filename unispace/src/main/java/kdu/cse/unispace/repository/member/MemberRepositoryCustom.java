package kdu.cse.unispace.repository.member;

import kdu.cse.unispace.domain.Member;

import java.util.List;

public interface MemberRepositoryCustom {
    List<Member> searchMembersByName(String query, int limit);
}
