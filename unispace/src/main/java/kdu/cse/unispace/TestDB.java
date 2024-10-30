package kdu.cse.unispace;

import jakarta.annotation.PostConstruct;
import kdu.cse.unispace.domain.Member;
import kdu.cse.unispace.domain.Team;
import kdu.cse.unispace.domain.friend.FriendShip;
import kdu.cse.unispace.domain.space.schedule.Category;
import kdu.cse.unispace.domain.space.schedule.PublicSetting;
import kdu.cse.unispace.domain.space.schedule.Schedule;
import kdu.cse.unispace.domain.space.schedule.ToDo;
import kdu.cse.unispace.requestdto.member.MemberJoinRequestDto;
import kdu.cse.unispace.requestdto.schedule.category.CategoryRequestDto;
import kdu.cse.unispace.requestdto.space.page.PageCreateRequestDto;
import kdu.cse.unispace.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TestDB {

    private final CategoryService categoryService;
    private final ScheduleService scheduleService;
    private final ToDoService toDoService;
    private final FriendShipService friendShipService;
    private final MemberService memberService;
    private final TeamService teamService;
    private final PageService pageService;
    private final BlockService blockService;
    private final PasswordEncoder passwordEncoder;
    private final MessageService messageService;
    private final RoomService roomService;

    @PostConstruct
    public void postConstruct() {
        initializeDatabase();
    }

    @Transactional
    public void initializeDatabase() {
        pageInit();
        teamInit();
        friendInit();
        scheduleInit();
    }

    @Transactional
    public void pageInit() {
        Long memberId = createMember("page@naver.com", "pageMember", "password");
        Long spaceId = memberService.findOne(memberId).getMemberSpace().getId();

        Long firstPageId = createPage(spaceId, "페이지 제목", null);
        createPage(spaceId, "페이지 제목2", Optional.of(firstPageId));
        createPage(spaceId, "페이지 제목3", Optional.of(firstPageId));

        blockService.makeBlock(firstPageId, memberId);
        blockService.makeBlock(firstPageId, memberId);
    }

    @Transactional
    public void teamInit() {
        Long memberAId = createMember("aaaT@naver.com", "memberA_makeTeam", "password1");
        Long memberBId = createMember("bbbT@naver.com", "memberB_makeTeam", "password2");
        Long memberCId = createMember("cccT@naver.com", "memberC_makeTeam", "password3");

        Long teamId = teamService.makeTeam(memberService.findOne(memberAId), "A가 만든 팀");
        teamService.join(memberService.findOne(memberBId), teamId);
        teamService.join(memberService.findOne(memberCId), teamId);
    }

    @Transactional
    public void friendInit() {
        Long memberAId = createMember("aaa@naver.com", "memberA", "password1");
        Long memberBId = createMember("bbb@naver.com", "memberB", "password2");
        Long memberCId = createMember("ccc@naver.com", "memberC", "password3");

        addFriend(memberAId, memberBId);
        addFriend(memberBId, memberAId);
        addFriend(memberAId, memberCId);
        addFriend(memberCId, memberAId);
    }

    @Transactional
    public void scheduleInit() {
        Long memberDId = createMember("test1@naver.com", "memberD", "password4");
        Member memberD = memberService.findOne(memberDId);
        Schedule schedule = memberD.getMemberSpace().getSchedule();

        createCategory(schedule, "공부", PublicSetting.PUBLIC);
        createCategory(schedule, "운동", PublicSetting.PRIVATE);

        createTodo(schedule, "스프링 공부");
        createTodo(schedule, "코딩 공부");
        createTodo(schedule, "헬스");

        Long memberAId = createMember("test2@naver.com", "memberA : D의 팀원+D의 친구", "password1");
        Long memberBId = createMember("test3@naver.com", "memberB : D의 팀원+D의 친구", "password2");
        Long memberCId = createMember("test4@naver.com", "memberC : D의 팀원", "password3");

        addFriend(memberAId, memberDId);
        addFriend(memberDId, memberAId);
        addFriend(memberDId, memberBId);
        addFriend(memberBId, memberDId);

        Long teamId1 = teamService.makeTeam(memberD, "D(member1)가 만든 팀 1");
        Long teamId2 = teamService.makeTeam(memberD, "D(member1)가 만든 팀 2");

        teamService.join(memberService.findOne(memberAId), teamId1);
        teamService.join(memberService.findOne(memberBId), teamId1);
        teamService.join(memberService.findOne(memberCId), teamId2);

        Long spaceId = memberD.getMemberSpace().getId();
        Long firstPageId = createPage(spaceId, "페이지 제목", null);
        createPage(spaceId, "페이지 제목2", Optional.of(firstPageId));
        createPage(spaceId, "페이지 제목3", Optional.of(firstPageId));

        Long blockId1 = blockService.makeBlock(firstPageId, memberDId);
        Long teamSpaceId = teamService.findOne(teamId1).getTeamSpace().getId();

        createPage(teamSpaceId, "팀에게 줄 페이지1", null);
        createPage(teamSpaceId, "팀에게 줄 페이지2", Optional.of(firstPageId));
        createPage(teamSpaceId, "팀에게 줄 페이지3", Optional.of(firstPageId));
    }

    private Long createMember(String email, String memberName, String password) {
        MemberJoinRequestDto requestDto = new MemberJoinRequestDto(email, memberName, passwordEncoder.encode(password));
        return memberService.join(requestDto);
    }

    private Long createPage(Long spaceId, String title, Optional<Long> previousPageId) {
        PageCreateRequestDto requestDto = new PageCreateRequestDto(title, previousPageId);
        return pageService.makePage(spaceId, requestDto);
    }

    private void createCategory(Schedule schedule, String name, PublicSetting setting) {
        Category category = new Category(schedule, new CategoryRequestDto(name, setting, ""));
        categoryService.makeCategory(category);
    }

    private void createTodo(Schedule schedule, String title) {
        ToDo todo = new ToDo(schedule.getCategories().get(0), title, false, LocalDateTime.now(), true, null);
        toDoService.makeTodo(todo);
    }

    private void addFriend(Long memberIdA, Long memberIdB) {
        Member memberA = memberService.findOne(memberIdA);
        Member memberB = memberService.findOne(memberIdB);
        friendShipService.addFriend(new FriendShip(memberA, memberB));
    }
}
