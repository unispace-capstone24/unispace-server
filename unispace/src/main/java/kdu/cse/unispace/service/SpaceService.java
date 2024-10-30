package kdu.cse.unispace.service;

import kdu.cse.unispace.domain.Member;
import kdu.cse.unispace.domain.Team;
import kdu.cse.unispace.domain.space.MemberSpace;
import kdu.cse.unispace.domain.space.Space;
import kdu.cse.unispace.domain.space.TeamSpace;
import kdu.cse.unispace.domain.space.TrashCan;
import kdu.cse.unispace.repository.SpaceRepository;
import kdu.cse.unispace.exception.space.SpaceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class SpaceService {

    private final SpaceRepository spaceRepository;
    private final TrashCanService trashCanService;

    public kdu.cse.unispace.domain.space.Space findOne(Long spaceId) {
        return spaceRepository.findById(spaceId).orElseThrow(()
                -> new SpaceNotFoundException("해당하는 스페이스가 존재하지 않습니다."));
    }

    public Space makeMemberSpace(Member member) { //Member생성자의 스페이스를 저장, 연결
        MemberSpace memberSpace = member.getMemberSpace();

        //휴지통 생성, 연결
        TrashCan trashCan = trashCanService.makeTrashCan(memberSpace);
        memberSpace.makeTrashCanRelation(trashCan);
        spaceRepository.save(memberSpace);

        return findOne(memberSpace.getId());
    }

    public Space makeTeamSpace(Team team) { //Team생성자의 스페이스를 저장, 연결
        TeamSpace teamSpace = team.getTeamSpace();

        //휴지통 생성, 연결
        TrashCan trashCan = trashCanService.makeTrashCan(teamSpace);
        teamSpace.makeTrashCanRelation(trashCan);

        spaceRepository.save(teamSpace);
        return findOne(teamSpace.getId());
    }


}
