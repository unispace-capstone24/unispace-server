package kdu.cse.unispace.repository.team;

import com.querydsl.jpa.impl.JPAQuery;
import kdu.cse.unispace.domain.QTeam;
import kdu.cse.unispace.domain.Team;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

public class TeamRepositoryImpl extends QuerydslRepositorySupport implements TeamRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    public TeamRepositoryImpl() {
        super(Team.class);
    }

    @Override
    public List<Team> searchTeamsByName(String query, int limit) {
        QTeam team = QTeam.team;

        JPAQuery<Team> jpaQuery = new JPAQuery<>(entityManager);

        return jpaQuery.from(team)
                .where(team.teamName.containsIgnoreCase(query))
                .orderBy(team.teamName.asc())
                .limit(limit)
                .fetch();
    }
}
