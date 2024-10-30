package kdu.cse.unispace.domain.space.schedule;

import jakarta.persistence.*;
import kdu.cse.unispace.requestdto.schedule.category.CategoryDailyEasyDto;
import kdu.cse.unispace.requestdto.schedule.category.CategoryMonthlyEasyDto;
import kdu.cse.unispace.requestdto.schedule.category.CategoryWeeklyEasyDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EasyToDo {
    @Id
    @GeneratedValue
    @Column(name = "todo_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    private String description;
    //private Boolean completed;
    private LocalDateTime date;
    //private boolean active;
    private UUID easyMake; //간편등록시에만 사용됨

    private LocalDateTime start; //간편등록시에만 사용됨

    private LocalDateTime end; //간편등록시에만 사용됨

    @Enumerated(EnumType.STRING)
    private EasyType type;

    @ElementCollection
    private List<DayOfWeek> weeks;

    @ElementCollection
    private List<Integer> months;

    private boolean lastDay;



    public EasyToDo(Category category, String description, LocalDateTime startDay,
                    LocalDateTime endDay, UUID uuid, EasyType type) {
        this.category = category;
        this.description = description;
        this.start = startDay;
        this.end = endDay;
        this.date = LocalDateTime.now();

        this.easyMake = uuid;

        this.type = type;
    }


    public void changeDescription(String description) {
        this.description = description;
    }

//    public void updateCompleted(Boolean completed) {
//        this.completed = completed;
//    }
//
//    public void changeActive() {
//        if (this.active) {
//            throw new ToDoActiveException("이미 활성화중인 투두입니다.");
//        }
//        this.active = true;
//    }

    public void changeDate(LocalDateTime start, LocalDateTime end ) {
        this.start = start;
        this.end = end;
    }


    //----------------------------------------

    /**
     *  POST 등록
     */


    public void changeWeek(CategoryWeeklyEasyDto weeklyDto) {
        DayOfWeek[] week = weeklyDto.getWeek();
        weeks = new ArrayList<>(week.length);
        weeks.addAll(Arrays.asList(week));
    }
    public void changeMonth(CategoryMonthlyEasyDto monthlyDto) {


        Integer[] day = monthlyDto.getDay();
        months = new ArrayList<>(day.length);
        months.addAll(Arrays.asList(day));

        if (monthlyDto.isLastDay()) {
            lastDay = true;
        }

    }

    //----------------------------------------

    /**
     * 수정
     */

    private void changeDetail(String description,
                              LocalDateTime startDay,
                              LocalDateTime endDay) {
        this.description = description;
        this.start = startDay;
        this.end = endDay;
    }

    public void changeDailyPatch(CategoryDailyEasyDto dailyDto) {
        changeDetail(dailyDto.getDescription(),
                dailyDto.getStartDay(), dailyDto.getEndDay());
        this.type = EasyType.DAILY;
        weeks = new ArrayList<>();
        months = new ArrayList<>();
    }
    public void changeWeeklyPatch(CategoryWeeklyEasyDto weeklyDto) {
        changeDetail( weeklyDto.getDescription(),
                weeklyDto.getStartDay(),  weeklyDto.getEndDay());
        this.type = EasyType.WEEKLY;

        changeWeek(weeklyDto);

        months = new ArrayList<>();
    }
    public void changeMonthlyPatch( CategoryMonthlyEasyDto monthlyDto) {
        changeDetail(monthlyDto.getDescription(),
                monthlyDto.getStartDay(), monthlyDto.getEndDay());
        this.type = EasyType.MONTHLY;
        changeMonth(monthlyDto);

        weeks = new ArrayList<>();
    }


}
