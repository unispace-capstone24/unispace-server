package kdu.cse.unispace.repository.team;

import kdu.cse.unispace.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    Optional<Team> findByTeamName(String teamName);

    List<Team> searchTeamsByName(String query, int limit);

    Object save(Team team);
}
