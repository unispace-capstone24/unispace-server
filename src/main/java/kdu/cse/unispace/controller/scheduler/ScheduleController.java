package kdu.cse.unispace.controller.scheduler;

import kdu.cse.unispace.domain.Member;
import kdu.cse.unispace.domain.space.schedule.Category;
import kdu.cse.unispace.domain.space.schedule.Schedule;
import kdu.cse.unispace.domain.space.schedule.ToDo;
import kdu.cse.unispace.requestdto.schedule.category.CategoryInactiveDto;
import kdu.cse.unispace.requestdto.schedule.category.CategoryRequestDto;
import kdu.cse.unispace.requestdto.schedule.category.CategoryUpdateDto;
import kdu.cse.unispace.requestdto.schedule.todo.ToDoCompletedUpdateDto;
import kdu.cse.unispace.requestdto.schedule.todo.ToDoDescriptionUpdateDto;
import kdu.cse.unispace.requestdto.schedule.todo.ToDoRequestDto;
import kdu.cse.unispace.responsedto.BasicResponse;
import kdu.cse.unispace.responsedto.friend.FriendBasicResponse;
import kdu.cse.unispace.responsedto.schedule.ScheduleCategoryDto;
import kdu.cse.unispace.responsedto.schedule.ScheduleDto;
import kdu.cse.unispace.responsedto.schedule.ScheduleFriendDto;
import kdu.cse.unispace.responsedto.schedule.category.CategoriesDto;
import kdu.cse.unispace.responsedto.schedule.category.CategoryBasicResponse;
import kdu.cse.unispace.responsedto.schedule.easy.EasyCategory;
import kdu.cse.unispace.responsedto.schedule.todo.ToDoBasicResponse;
import kdu.cse.unispace.service.CategoryService;
import kdu.cse.unispace.service.MemberService;
import kdu.cse.unispace.service.ScheduleService;
import kdu.cse.unispace.service.ToDoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private static final int SUCCESS = 200;

    private static final int CREATED = 201;
    private final MemberService memberService;

    private final ScheduleService scheduleService;

    private final CategoryService categoryService;

    private final ToDoService toDoService;

    @GetMapping("/schedule/{scheduleId}") //스케줄 조회
    @PreAuthorize("@jwtAuthenticationFilter.isScheduleOwner(#request, #scheduleId)")
    public ResponseEntity<BasicResponse> schedule(@PathVariable("scheduleId") Long scheduleId,
                                                  HttpServletRequest request) {
        Schedule schedule = scheduleService.findSchedule(scheduleId);
        List<ScheduleDto> collect = Collections.singletonList(new ScheduleDto(schedule));
        BasicResponse basicResponse = new BasicResponse<>(collect.size(), "스케줄 요청 성공", collect.get(0));
        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
    }
    @GetMapping("/friend/{friendId}/schedule") //친구 스케줄 조회
    @PreAuthorize("@jwtAuthenticationFilter.isFriend(#request, #friendId)")  //서로 친구인지 확인
    public ResponseEntity<BasicResponse> getFriendSchedule(@PathVariable("friendId") Long friendId, HttpServletRequest request) {
        Member friend = memberService.findOne(friendId);
        Schedule friendSchedule = friend.getMemberSpace().getSchedule();
        List<ScheduleFriendDto> collect = Collections.singletonList(new ScheduleFriendDto(friendSchedule ));
        BasicResponse basicResponse = new BasicResponse<>(collect.size(), "스케줄 요청 성공", collect.get(0));
        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
    }

    @GetMapping("/schedule/{scheduleId}/categories") //카테고리 조회
    @PreAuthorize("@jwtAuthenticationFilter.isScheduleOwner(#request, #scheduleId)")
    public ResponseEntity<BasicResponse> categories(@PathVariable("scheduleId") Long scheduleId,
                                                    HttpServletRequest request) {
        Schedule schedule = scheduleService.findSchedule(scheduleId);
        List<ScheduleCategoryDto> collect = Collections.singletonList(new ScheduleCategoryDto(schedule));
        BasicResponse basicResponse = new BasicResponse<>(collect.size(), "카테고리들 요청 성공", collect.get(0));
        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
    }
    @GetMapping("/schedule/{scheduleId}/easytodo") //간편입력 조회
    @PreAuthorize("@jwtAuthenticationFilter.isScheduleOwner(#request, #scheduleId)")
    public ResponseEntity<List<EasyCategory>> easyToDo(@PathVariable("scheduleId") Long scheduleId,
                                                       HttpServletRequest request) {
        List<EasyCategory> easyToDo = scheduleService.findEasyToDo(scheduleId);

        return new ResponseEntity<>(easyToDo, HttpStatus.OK);
    }

    /**
     * 카테고리 생성
     */
    @PostMapping("/schedule/{scheduleId}/category")
    @PreAuthorize("@jwtAuthenticationFilter.isScheduleOwner(#request, #scheduleId)")
    public ResponseEntity<CategoryBasicResponse> createCategory(@PathVariable("scheduleId") Long scheduleId,
                                                                @RequestBody CategoryRequestDto categoryRequestDto,
                                                                HttpServletRequest request) {
        Schedule schedule = scheduleService.findSchedule(scheduleId);

        Category category = new Category(schedule, categoryRequestDto);

        Long saveCategoryId = categoryService.makeCategory(category);
        CategoryBasicResponse categoryBasicResponse = new CategoryBasicResponse(saveCategoryId, CREATED, "카테고리가 등록되었습니다.");
        return new ResponseEntity<>(categoryBasicResponse, HttpStatus.CREATED);
    }

    @PatchMapping("/todo/{todoId}") //투두 수정
    @PreAuthorize("@jwtAuthenticationFilter.isToDoOwner(#request,#todoId)")
    public ResponseEntity<ToDoBasicResponse> updateToDoDescription(@PathVariable("todoId") Long todoId,
                                                                   @RequestBody ToDoDescriptionUpdateDto toDoDescriptionUpdateDto,
                                                                   HttpServletRequest request) {
        Long updateToDoId = toDoService.updateToDo(todoId, toDoDescriptionUpdateDto.getDescription(), toDoDescriptionUpdateDto.getCompleted());
        ToDoBasicResponse toDoBasicResponse = new ToDoBasicResponse(updateToDoId, SUCCESS, "할일이 수정되었습니다.");
        return new ResponseEntity<>(toDoBasicResponse, HttpStatus.OK);
    }
    @DeleteMapping("/todo/{todoId}") //투두 삭제
    @PreAuthorize("@jwtAuthenticationFilter.isToDoOwner(#request,#todoId)")
    public ResponseEntity<ToDoBasicResponse> deleteToDo(@PathVariable("todoId") Long todoId,
                                                        HttpServletRequest request) {
        toDoService.deleteToDo(todoId);
        ToDoBasicResponse toDoBasicResponse = new ToDoBasicResponse(SUCCESS, "할일이 삭제되었습니다.");
        return new ResponseEntity<>(toDoBasicResponse, HttpStatus.OK);
    }
}