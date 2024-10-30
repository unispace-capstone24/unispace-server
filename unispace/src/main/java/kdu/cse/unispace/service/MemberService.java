package kdu.cse.unispace.service;

import kdu.cse.unispace.domain.Member;
import kdu.cse.unispace.domain.space.Space;
import kdu.cse.unispace.exception.member.MemberNotFoundException;
import kdu.cse.unispace.exception.member.join.DuplicateEmailException;
import kdu.cse.unispace.repository.member.MemberRepository;
import kdu.cse.unispace.requestdto.member.MemberJoinRequestDto;
import kdu.cse.unispace.requestdto.member.MemberUpdateRequestDto;
import kdu.cse.unispace.requestdto.space.page.PageCreateRequestDto;
import kdu.cse.unispace.responsedto.member.MemberSearchByNameDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.similarity.JaroWinklerDistance;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor // final 필드 생성자 주입 코드 자동 생성
public class MemberService {

    private final MemberRepository memberRepository;
    private final SpaceService spaceService;
    private final ScheduleService scheduleService;
    private final PageService pageService;

    @Transactional
    public void setMemberActive(Long memberId) {
        Member member = findOne(memberId);
        member.setStatus(true);
    }

    @Transactional
    public void setMemberInActive(Long memberId) {
        Member member = findOne(memberId);
        member.setStatus(false);
    }

    public List<Long> findMembersId() {
        return memberRepository.getAllMemberIds();
    }


    public kdu.cse.unispace.domain.Member findOne(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("회원을 찾을 수 없습니다."));
    }
    public kdu.cse.unispace.domain.Member findByUuid(UUID uuid) {
        return memberRepository.findByUuid(uuid)
                .orElseThrow(() -> new MemberNotFoundException("회원을 찾을 수 없습니다."));
    }

    public boolean existsByEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new DuplicateEmailException("이미 존재하는 email 입니다.");
        }
        return false;
    }

    @Transactional
    public Long join(MemberJoinRequestDto joinRequestDto) { //회원가입

        // 중복 이메일 검사
        existsByEmail(joinRequestDto.getEmail());

        Member member = new Member(UUID.randomUUID(), joinRequestDto.getMemberName(), joinRequestDto.getEmail(), joinRequestDto.getPassword());

        // 회원가입 시 스페이스 생성
        Space memberSpace = spaceService.makeMemberSpace(member);

        // 스케줄 생성
        scheduleService.makeSchedule(memberSpace);

        // 페이지 생성
        PageCreateRequestDto pageCreateRequestDto = new PageCreateRequestDto("새로운 페이지", null);
        pageService.makePage(memberSpace.getId(), pageCreateRequestDto);

        return member.getId();

    }

    public long memberCount() {
        return memberRepository.count();
    }




//    public Optional<Member> findOneWithMemberTeams(Long memberId){
//        return memberRepository.findByIdWithMemberTeams(memberId);
//    }

    @Transactional
    public void delete(Long memberId) {
        Member member = findOne(memberId);
        memberRepository.delete(member);
    }

    @Transactional
    public Long update(Long memberId, MemberUpdateRequestDto memberUpdateRequestDto) {

        Member member = findOne(memberId);

        String email = memberUpdateRequestDto.getEmail();
        String password = memberUpdateRequestDto.getPassword();
        String memberName = memberUpdateRequestDto.getMemberName();

        if (email != null || password != null || memberName != null) {
            member.update(email, password, memberName);
            memberRepository.save(member);
        }

        // 업데이트
        memberRepository.save(member);
        return member.getId();
    }


    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException("회원을 찾을 수 없습니다."));
    }


    public synchronized List<MemberSearchByNameDto> searchMembersByName(String query, int limit) {
        List<Member> members = memberRepository.searchMembersByName(query, limit);

        JaroWinklerDistance jaroWinkler = new JaroWinklerDistance();

        List<MemberSearchByNameDto> memberDTOs = members.stream()
                .map(member -> new MemberSearchByNameDto(member.getId(), member.getMemberName()))
                .sorted((member1, member2) -> {
                    // 정확한 일치를 최우선 순위로 정렬
                    boolean exactMatch1 = member1.getMemberName().equalsIgnoreCase(query);
                    boolean exactMatch2 = member2.getMemberName().equalsIgnoreCase(query);

                    if (exactMatch1 != exactMatch2) {
                        return exactMatch1 ? -1 : 1;
                    }

                    // 검색어에 포함된 단어의 빈도를 기준으로 정렬
                    String[] queryWords = query.toLowerCase().split(" ");
                    long count1 = Arrays.stream(queryWords).filter(word -> member1.getMemberName().toLowerCase().contains(word)).count();
                    long count2 = Arrays.stream(queryWords).filter(word -> member2.getMemberName().toLowerCase().contains(word)).count();

                    if (count1 != count2) {
                        return Long.compare(count2, count1);
                    }

                    // 검색어에 포함된 문자와 숫자를 개별적으로 확인하여 일치하는 경우 가중치 부여
                    int weight1 = 0;
                    int weight2 = 0;

                    for (char c : query.toCharArray()) {
                        if (member1.getMemberName().toLowerCase().contains(String.valueOf(c))) {
                            weight1++;
                        }
                        if (member2.getMemberName().toLowerCase().contains(String.valueOf(c))) {
                            weight2++;
                        }
                    }

                    if (weight1 != weight2) {
                        return Integer.compare(weight2, weight1);
                    }

                    // Jaro-Winkler 유사도를 기준으로 정렬
                    double similarity1 = jaroWinkler.apply(query, member1.getMemberName());
                    double similarity2 = jaroWinkler.apply(query, member2.getMemberName());

                    return Double.compare(similarity2, similarity1);
                })
                .limit(limit)
                .collect(Collectors.toList());

        return memberDTOs;
    }

    @Transactional
    public void save(Member member) {
        memberRepository.save(member);
    }
}
