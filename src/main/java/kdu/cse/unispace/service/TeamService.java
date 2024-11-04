package kdu.cse.unispace.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import kdu.cse.unispace.domain.*;
import kdu.cse.unispace.domain.space.Space;
import kdu.cse.unispace.exception.team.TeamNotFoundException;
import kdu.cse.unispace.repository.*;
import kdu.cse.unispace.repository.team.TeamRepository;
import kdu.cse.unispace.requestdto.space.page.PageCreateRequestDto;
import kdu.cse.unispace.responsedto.team.TeamSearchByNameDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.similarity.JaroWinklerDistance;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class TeamService {

    private final TeamRepository teamRepository;

    private final SpaceService spaceService;
    private final ScheduleService scheduleService;
    private final PageService pageService;
    private final RoomService roomService;
    private final MemberTeamRepository memberTeamRepository;
//    private final SpaceRepository spaceRepository;
//    private final ScheduleRepository scheduleRepository;
//    private final RoomRepository roomRepository;

    public Team findOne(Long teamId) {
        return teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException("팀을 찾을 수 없습니다."));
    }


    @Transactional
    public Long makeTeam(Member member, String teamName) { //팀 생성 - 팀을 생성하는 회원에게는 바로 팀 부여

        Team team = new Team(teamName, member.getId());

        teamRepository.save(team);

        makeMemberTeamRelation(member, team); //멤버팀 연관관계 생성

        //팀 생성시 스페이스 생성 + 부여
        Space teamSpace = spaceService.makeTeamSpace(team);
//        TeamSpace teamSpace = team.getTeamSpace();
//        spaceRepository.save(teamSpace);

        //페이지도 만들어서 하나 넣어줌
        PageCreateRequestDto pageCreateRequestDto = new PageCreateRequestDto("새로운 페이지", null);
        pageService.makePage(teamSpace.getId(), pageCreateRequestDto);
//        Page page = new Page("새로운 페이지");
//        page.makeRelationPageSpace(page, teamSpace);
//        teamSpace.getPageList().add(page);

        //스페이스 생성했으니 바로 스케줄도 만들어서 줌..
        scheduleService.makeSchedule(teamSpace);

        //채팅방도 하나 만들어서 넣어줌
        roomService.makeTeamChattingRoom(teamSpace, "General");

        return team.getId();
    }

    @Transactional
    public Long join(Member member, Long teamId) { //팀 가입
        Team team = findOne(teamId);

        MemberTeamId memberTeamId = new MemberTeamId(member.getId(), teamId);
        Optional<MemberTeam> memberTeamOptional = memberTeamRepository.findById(memberTeamId);
        MemberTeam memberTeam = memberTeamOptional.orElse(null);

        if (memberTeam == null) { //이미 가입되어있는 경우
            makeMemberTeamRelation(member, team);
        }

        return teamId;
    }

    @Transactional
    public void makeMemberTeamRelation(Member member, Team team) {

        // 멤버-팀 관계 생성
        MemberTeam memberTeam = new MemberTeam(member, team);
        team.joinTeam(team);
        memberTeamRepository.save(memberTeam);

        // 멤버 - 멤버팀 - 팀 이어주기
        member.getMemberTeams().add(memberTeam);
        team.getMemberTeams().add(memberTeam);


    }


    @Transactional
    public void deleteTeam(Long teamId) {
        Team team = findOne(teamId);

        QMemberTeam memberTeam = QMemberTeam.memberTeam;
        BooleanExpression teamIdEquals = memberTeam.team.id.eq(teamId);
        Iterable<MemberTeam> memberTeams = memberTeamRepository.findAll(teamIdEquals);
        memberTeamRepository.deleteAll(memberTeams);


        teamRepository.delete(team);
    }

    public Team searchTeamByName(String teamName) {

        return teamRepository.findByTeamName(teamName)
                .orElseThrow(() -> new TeamNotFoundException("팀을 찾을 수 없습니다."));
    }

    public synchronized List<TeamSearchByNameDto> searchTeamsByName(String query, int limit) {
        List<Team> teams = teamRepository.searchTeamsByName(query, limit);

        JaroWinklerDistance jaroWinkler = new JaroWinklerDistance();

        List<TeamSearchByNameDto> teamDTOs = teams.stream()
                .map(team -> new TeamSearchByNameDto(team.getId(), team.getTeamName()))
                .sorted((team1, team2) -> {
                    // 정확한 일치를 최우선 순위로 정렬
                    boolean exactMatch1 = team1.getTeamName().equalsIgnoreCase(query);
                    boolean exactMatch2 = team2.getTeamName().equalsIgnoreCase(query);

                    if (exactMatch1 != exactMatch2) {
                        return exactMatch1 ? -1 : 1;
                    }

                    // 검색어에 포함된 단어의 빈도를 기준으로 정렬
                    String[] queryWords = query.toLowerCase().split(" ");
                    long count1 = Arrays.stream(queryWords).filter(word -> team1.getTeamName().toLowerCase().contains(word)).count();
                    long count2 = Arrays.stream(queryWords).filter(word -> team2.getTeamName().toLowerCase().contains(word)).count();

                    if (count1 != count2) {
                        return Long.compare(count2, count1);
                    }

                    // 검색어에 포함된 문자와 숫자를 개별적으로 확인하여 일치하는 경우 가중치 부여
                    int weight1 = 0;
                    int weight2 = 0;

                    for (char c : query.toCharArray()) {
                        if (team1.getTeamName().toLowerCase().contains(String.valueOf(c))) {
                            weight1++;
                        }
                        if (team2.getTeamName().toLowerCase().contains(String.valueOf(c))) {
                            weight2++;
                        }
                    }

                    if (weight1 != weight2) {
                        return Integer.compare(weight2, weight1);
                    }

                    // Jaro-Winkler 유사도를 기준으로 정렬
                    double similarity1 = jaroWinkler.apply(query, team1.getTeamName());
                    double similarity2 = jaroWinkler.apply(query, team2.getTeamName());

                    return Double.compare(similarity2, similarity1);
                })
                .limit(limit)
                .collect(Collectors.toList());

        return teamDTOs;
    }

    @Transactional
    public void getOutTeam(Long teamId, Long memberId) {

        MemberTeam memberTeam =
                memberTeamRepository.findByMemberIdAndTeamId(memberId, teamId)
                        .orElseThrow(() -> new TeamNotFoundException("팀Id와 관계된 팀을 찾지 못했습니다."));

        Team team = memberTeam.getTeam();
        int memberCount = team.decreaseMemberCount();
        memberTeamRepository.delete(memberTeam);

        if (memberCount == 0) {
            teamRepository.delete(team);
        }

    }


//    private int commonStringLength(String query, String teamName) {
//        int count = 0;
//        for (int i = 0; i < query.length() && i < teamName.length(); i++) {
//            if (query.charAt(i) == teamName.charAt(i)) {
//                count++;
//            } else {
//                break;
//            }
//        }
//        return count;
//    }

}