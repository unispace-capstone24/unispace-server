package kdu.cse.unispace.repository.team;

import kdu.cse.unispace.domain.Team;

import java.util.List;

public interface TeamRepositoryCustom {
    List<Team> searchTeamsByName(String query, int limit);
}