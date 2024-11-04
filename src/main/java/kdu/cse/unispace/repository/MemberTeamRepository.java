package kdu.cse.unispace.repository;

import com.querydsl.core.types.Predicate;
import kdu.cse.unispace.domain.MemberTeam;
import kdu.cse.unispace.domain.MemberTeamId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberTeamRepository extends JpaRepository<MemberTeam, MemberTeamId>, QuerydslPredicateExecutor<MemberTeam> {
    @Query("SELECT mt FROM MemberTeam mt WHERE mt.team.id = :teamId")
    List<MemberTeam> findByTeamId(@Param("teamId") Long teamId);

    List<MemberTeam> findAll(Predicate predicate);

    Optional<MemberTeam> findByMemberIdAndTeamId(Long memberId, Long  teamId);

}