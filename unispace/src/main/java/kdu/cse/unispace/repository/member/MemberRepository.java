package kdu.cse.unispace.repository.member;

import kdu.cse.unispace.domain.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @EntityGraph(attributePaths = "memberTeams") //팀을 생성한 멤버 전용
    Optional<Member> findById(Long id);

    Optional<Member> findByEmail(String email);

    Optional<Member> findByMemberName(String memberName);
    Optional<Member> findByUuid(UUID uuid);

    boolean existsByEmail(String email); //이메일 중복검사시 사용

    List<Member> searchMembersByName(String query, int limit);

    @Query("SELECT DISTINCT m.id FROM Member m")
    List<Long> getAllMemberIds();
}