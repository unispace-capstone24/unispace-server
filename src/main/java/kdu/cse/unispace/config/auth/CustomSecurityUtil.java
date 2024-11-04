package kdu.cse.unispace.config.auth;

import kdu.cse.unispace.domain.Member;
import kdu.cse.unispace.domain.MemberTeam;
import kdu.cse.unispace.domain.Team;
import kdu.cse.unispace.domain.chat.Room;
import kdu.cse.unispace.domain.space.Page;
import kdu.cse.unispace.domain.space.Space;
import kdu.cse.unispace.domain.space.schedule.Category;
import kdu.cse.unispace.domain.space.schedule.Schedule;
import kdu.cse.unispace.domain.space.schedule.ToDo;
import kdu.cse.unispace.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomSecurityUtil {

    private final TeamService teamService;
    private final MemberService memberService;
    private final SpaceService spaceService;
    private final PageService pageService;
    private final ScheduleService scheduleService;
    private final CategoryService categoryService;
    private final ToDoService toDoService;
    private final RoomService roomService;

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public boolean isMemberOwner(Long memberId) {
        Member memberOpt = memberService.findOne(memberId);
        if (memberOpt.isEmpty()) {
            throw new RuntimeException("Member not found");
        }
        Authentication authentication = getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Long currentUserId = Long.valueOf(userDetails.getId());
            return currentUserId.equals(memberId);
        }
        return false;
    }

    public boolean isTeamHost(Long teamId) {
        Team teamOpt = teamService.findOne(teamId);
        if (teamOpt.isEmpty()) {
            throw new RuntimeException("Team not found");
        }
        Team team = teamOpt.get();
        Authentication authentication = getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Long currentUserId = Long.valueOf(userDetails.getId());
            return currentUserId.equals(team.getHost());
        }
        return false;
    }

    public boolean isMemberInTeam(Long teamId) {
        Team teamOpt = teamService.findOne(teamId);
        if (teamOpt.isEmpty()) {
            throw new RuntimeException("Team not found");
        }
        Authentication authentication = getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Long currentUserId = Long.valueOf(userDetails.getId());
            Member memberOpt = memberService.findOne(currentUserId);
            if (memberOpt.isEmpty()) {
                throw new RuntimeException("Member not found");
            }
            Member member = memberOpt.get();
            return member.getMemberTeams().stream()
                    .map(MemberTeam::getTeam)
                    .anyMatch(teams -> teams.getId().equals(teamId));
        }
        return false;
    }

    public boolean isSpaceOwner(Long spaceId) {
        Space spaceOpt = spaceService.findOne(spaceId);
        if (spaceOpt.isEmpty()) {
            throw new RuntimeException("Space not found");
        }
        Authentication authentication = getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Long currentUserId = Long.valueOf(userDetails.getId());
            Member memberOpt = memberService.findOne(currentUserId);
            if (memberOpt.isEmpty()) {
                throw new RuntimeException("Member not found");
            }
            Member member = memberOpt.get();
            boolean memberSpaceOwner = member.getMemberSpace().getId().equals(spaceId);
            boolean teamSpaceOwner = member.getMemberTeams().stream()
                    .map(MemberTeam::getTeam)
                    .filter(Objects::nonNull)
                    .map(Team::getTeamSpace)
                    .filter(Objects::nonNull)
                    .anyMatch(teamSpace -> teamSpace.getId().equals(spaceId));
            return (memberSpaceOwner || teamSpaceOwner);
        }
        return false;
    }

    public boolean isPageOwner(Long spaceId) {
        spaceService.findOne(spaceId);
        Authentication authentication = getAuthenticaiton();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Long currentUserId = Long.valueOf(userDetails.getId());

            Member member = memberService.findOne(currentUserId);

            boolean memberSpaceOwner = member.getMemberSpace().getId().equals(spaceId);

            boolean teamSpaceOwner = member.getMemberTeams().stream()
                    .map(MemberTeam::getTeam)
                    .filter(Objects::nonNull)
                    .map(Team::getTeamSpace)
                    .filter(Objects::nonNull)
                    .anyMatch(teamSpace -> teamSpace.getId().equals(spaceId));

            return (memberSpaceOwner || teamSpaceOwner);
        }
        return false;
    }

    private Authentication getAuthenticaiton() {
        return null;
    }

    public boolean isBlockOwner(Long blockId) {
        return false;  // 블록 소유권 체크 로직을 구현하세요.
    }

    public boolean isScheduleOwner(Long scheduleId) {
        Schedule schedule = scheduleService.findSchedule(scheduleId);
        Long spaceId = schedule.getSpace().getId();
        return isSpaceOwner(spaceId);
    }

    public boolean isCategoryOwner(Long categoryId) {
        Category category = categoryService.findCategory(categoryId);
        return isScheduleOwner(category.getSchedule().getId());
    }

    public boolean isToDoOwner(Long toDoId) {
        ToDo toDo = toDoService.findTodo(toDoId);
        return isCategoryOwner(toDo.getCategory().getId());
    }

    public boolean isRoomOwner(Long roomId) {
        Room room = roomService.findOne(roomId);
        return isSpaceOwner(room.getSpace().getId());
    }
}
