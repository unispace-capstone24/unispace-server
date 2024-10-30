package kdu.cse.unispace.controller.scheduler;

import kdu.cse.unispace.domain.space.schedule.Category;
import kdu.cse.unispace.domain.space.schedule.EasyToDo;
import kdu.cse.unispace.domain.space.schedule.ToDo;
import kdu.cse.unispace.requestdto.schedule.category.CategoryDailyEasyDto;
import kdu.cse.unispace.requestdto.schedule.category.CategoryInactiveDto;
import kdu.cse.unispace.requestdto.schedule.category.CategoryMonthlyEasyDto;
import kdu.cse.unispace.requestdto.schedule.category.CategoryUpdateDto;
import kdu.cse.unispace.requestdto.schedule.category.CategoryWeeklyEasyDto;
import kdu.cse.unispace.requestdto.schedule.todo.ToDoRequestDto;
import kdu.cse.unispace.responsedto.schedule.category.CategoryBasicResponse;
import kdu.cse.unispace.responsedto.schedule.todo.ToDoBasicResponse;
import kdu.cse.unispace.responsedto.schedule.todo.ToDoEasyResponse;
import kdu.cse.unispace.service.CategoryService;
import kdu.cse.unispace.service.ToDoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class CategoryController {
    private static final int SUCCESS = 200;
    private static final int CREATED = 201;

    private final CategoryService categoryService;
    private final ToDoService toDoService;



    @PatchMapping("/category/{categoryId}") //카테고리 수정
    @PreAuthorize("@jwtAuthenticationFilter.isCategoryOwner(#request, #categoryId)")
    public ResponseEntity<CategoryBasicResponse> changeCategoryTitle(@PathVariable("categoryId") Long categoryId,
                                                                     @RequestBody CategoryUpdateDto categoryUpdateDto,
                                                                     HttpServletRequest request) {
        Long updateCategoryId = categoryService.update(categoryId, categoryUpdateDto);
        CategoryBasicResponse categoryBasicResponse = new CategoryBasicResponse(updateCategoryId, SUCCESS,
                "카테고리가 수정되었습니다.");
        return new ResponseEntity<>(categoryBasicResponse, HttpStatus.OK);
    }

    //---------------------------------------------------------------------------------

    @PatchMapping("/category/{categoryId}/inactive") //카테고리 비활성화
    @PreAuthorize("@jwtAuthenticationFilter.isCategoryOwner(#request, #categoryId)")
    public ResponseEntity<CategoryBasicResponse> inActiveCategory(@PathVariable("categoryId") Long categoryId,
                                                                  @Valid @RequestBody CategoryInactiveDto categoryInactiveDto,
                                                                  HttpServletRequest request) {
        Long updateCategoryId = categoryService.inActiveCategory(categoryId, categoryInactiveDto);
        CategoryBasicResponse categoryBasicResponse = new CategoryBasicResponse(updateCategoryId, SUCCESS,
                "카테고리가 비활성화 되었습니다.");
        return new ResponseEntity<>(categoryBasicResponse, HttpStatus.OK);
    }
    @PatchMapping("/category/{categoryId}/active") //카테고리 활성화
    @PreAuthorize("@jwtAuthenticationFilter.isCategoryOwner(#request, #categoryId)")
    public ResponseEntity<CategoryBasicResponse> activeCategory(@PathVariable("categoryId") Long categoryId,
                                                                HttpServletRequest request) {
        Long updateCategoryId = categoryService.activeCategory(categoryId);
        CategoryBasicResponse categoryBasicResponse = new CategoryBasicResponse(updateCategoryId, SUCCESS,
                "카테고리가 활성화 되었습니다.");
        return new ResponseEntity<>(categoryBasicResponse, HttpStatus.OK);
    }

    //---------------------------------------------------------------------------------

    @PostMapping("/category/{categoryId}/todo/daily") // 간편입력 - 매일반복
    @PreAuthorize("@jwtAuthenticationFilter.isCategoryOwner(#request, #categoryId)")
    public ResponseEntity<ToDoEasyResponse> makeDailyEasy(@PathVariable("categoryId") Long categoryId,
                                                                HttpServletRequest request,
                                                               @Valid @RequestBody CategoryDailyEasyDto dailyDto) {
        Long easyToDoId  = categoryService.makeDailyEasy(categoryId, dailyDto);
        ToDoEasyResponse toDoEasyResponse = new ToDoEasyResponse(easyToDoId, CREATED,
                "할일 간편입력이 완료되었습니다. - 매일 반복");
        return new ResponseEntity<>(toDoEasyResponse, HttpStatus.OK);
    }

    @PostMapping("/category/{categoryId}/todo/weekly") // 간편입력 - 매주반복
    @PreAuthorize("@jwtAuthenticationFilter.isCategoryOwner(#request, #categoryId)")
    public ResponseEntity<ToDoEasyResponse> makeWeeklyEasy(@PathVariable("categoryId") Long categoryId,
                                                               HttpServletRequest request,
                                                               @Valid @RequestBody CategoryWeeklyEasyDto weeklyDto) {
        Long easyToDoId = categoryService.makeWeeklyEasy(categoryId, weeklyDto);
        ToDoEasyResponse toDoEasyResponse = new ToDoEasyResponse(easyToDoId, CREATED,
                "할일 간편입력이 완료되었습니다. - 매주 반복");
        return new ResponseEntity<>(toDoEasyResponse, HttpStatus.OK);
    }

    @PostMapping("/category/{categoryId}/todo/monthly") // 간편입력 - 매월반복
    @PreAuthorize("@jwtAuthenticationFilter.isCategoryOwner(#request, #categoryId)")
    public ResponseEntity<ToDoEasyResponse> makeMonthlyEasy(@PathVariable("categoryId") Long categoryId,
                                                                HttpServletRequest request,
                                                                @Valid @RequestBody CategoryMonthlyEasyDto monthlyDto) {
        Long easyToDoId = categoryService.makeMonthlyDtoEasy(categoryId, monthlyDto);
        ToDoEasyResponse toDoEasyResponse = new ToDoEasyResponse(easyToDoId, CREATED,
                "할일 간편입력이 완료되었습니다. - 매월 반복");
        return new ResponseEntity<>(toDoEasyResponse, HttpStatus.OK);
    }

    //---------------------------------------------------------------------------------

    @PatchMapping("/category/{categoryId}/todo/{easyToDoId}/daily") // 간편입력 수정 - 매일반복
    @PreAuthorize("@jwtAuthenticationFilter.isCategoryOwner(#request, #categoryId)")
    public ResponseEntity<ToDoEasyResponse> modifyDailyEasy(@PathVariable("categoryId") Long categoryId,
                                                            @PathVariable("easyToDoId") Long easyToDoId,
                                                          HttpServletRequest request,
                                                          @Valid @RequestBody CategoryDailyEasyDto dailyDto) {
        Long easyId = categoryService.modifyDailyEasy(easyToDoId, dailyDto);
        ToDoEasyResponse toDoEasyResponse = new ToDoEasyResponse(easyId , SUCCESS,
                "할일 간편입력이 수정되었습니다. - 매일 반복");
        return new ResponseEntity<>(toDoEasyResponse, HttpStatus.OK);
    }

    @PatchMapping("/category/{categoryId}/todo/{easyToDoId}/weekly") // 간편입력 수정 - 매주반복
    @PreAuthorize("@jwtAuthenticationFilter.isCategoryOwner(#request, #categoryId)")
    public ResponseEntity<ToDoEasyResponse> modifyWeeklyEasy(@PathVariable("categoryId") Long categoryId,
                                                             @PathVariable("easyToDoId") Long easyToDoId,
                                                           HttpServletRequest request,
                                                           @Valid @RequestBody CategoryWeeklyEasyDto weeklyDto) {
        Long easyId = categoryService.modifyWeeklyEasy(easyToDoId, weeklyDto);
        ToDoEasyResponse toDoEasyResponse = new ToDoEasyResponse(easyId , SUCCESS,
                "할일 간편입력이 수정되었습니다. - 매주 반복");
        return new ResponseEntity<>(toDoEasyResponse, HttpStatus.OK);
    }

    @PatchMapping("/category/{categoryId}/todo/{easyToDoId}/monthly") // 간편입력 수정 - 매월반복
    @PreAuthorize("@jwtAuthenticationFilter.isCategoryOwner(#request, #categoryId)")
    public ResponseEntity<ToDoEasyResponse> modifyMonthlyEasy(@PathVariable("categoryId") Long categoryId,
                                                              @PathVariable("easyToDoId") Long easyToDoId,
                                                            HttpServletRequest request,
                                                            @Valid @RequestBody CategoryMonthlyEasyDto monthlyDto) {
        Long easyId = categoryService.modifyMonthlyDtoEasy(easyToDoId, monthlyDto);
        ToDoEasyResponse toDoEasyResponse = new ToDoEasyResponse(easyId , SUCCESS,
                "할일 간편입력이 수정되었습니다. - 매월 반복");
        return new ResponseEntity<>(toDoEasyResponse, HttpStatus.OK);
    }

    //---------------------------------------------------------------------------------

    @DeleteMapping("/category/{categoryId}") //카테고리 삭제
    @PreAuthorize("@jwtAuthenticationFilter.isCategoryOwner(#request, #categoryId)")
    public ResponseEntity<CategoryBasicResponse> deleteCategory(@PathVariable("categoryId") Long categoryId,
                                                                HttpServletRequest request) {
        categoryService.delete(categoryId);
        CategoryBasicResponse categoryBasicResponse = new CategoryBasicResponse(SUCCESS,
                "카테고리가 삭제되었습니다.");
        return new ResponseEntity<>(categoryBasicResponse, HttpStatus.OK);
    }

    @DeleteMapping("/category/{categoryId}/todo/{easyToDoId}") // 간편입력 해제
    @PreAuthorize("@jwtAuthenticationFilter.isCategoryOwner(#request, #categoryId)")
    public ResponseEntity<CategoryBasicResponse> deleteEasy(@PathVariable("categoryId") Long categoryId,
                                                            @PathVariable("easyToDoId") Long easyToDoId,
                                                                HttpServletRequest request) {
        EasyToDo easyToDo = categoryService.findEasyToDoById(easyToDoId);
        categoryService.deleteInactiveToDos(easyToDo.getEasyMake()); //엮여있는 투두들 삭제
        categoryService.deleteEasyToDo(easyToDo);
        CategoryBasicResponse categoryBasicResponse = new CategoryBasicResponse(SUCCESS,
                "간편입력이 삭제되었습니다.");
        return new ResponseEntity<>(categoryBasicResponse, HttpStatus.OK);
    }

}
