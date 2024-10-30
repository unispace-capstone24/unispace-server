package kdu.cse.unispace.responsedto.schedule.easy;

import kdu.cse.unispace.domain.space.schedule.EasyToDo;
import kdu.cse.unispace.domain.space.schedule.EasyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EasyToDoDto {
    private Long easyToDoId;
    private String description;
    private LocalDateTime start;
    private LocalDateTime end;
    private EasyType easyType;
    private String detailDate;

    public EasyToDoDto(EasyToDo todo) {
        this.easyToDoId = todo.getId();
        this.description = todo.getDescription();
        this.start = todo.getStart();
        this.end = todo.getEnd();
        this.easyType = todo.getType();
        if(easyType == EasyType.WEEKLY)
            detailDate = formatWeekList(todo.getWeeks());
        else if(easyType == EasyType.MONTHLY)
            detailDate = formatMonthList(todo.getMonths(), todo.isLastDay());

    }

    public String formatWeekList(List<DayOfWeek> weeks) {
        if (weeks == null || weeks.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (DayOfWeek week : weeks) {
            String dayName = getDayName(week);
            sb.append(dayName).append(", ");
        }

        // 마지막 쉼표와 공백 제거
        sb.setLength(sb.length() - 2);

        return sb.toString();
    }
    private String getDayName(DayOfWeek dayOfWeek) {
        switch (dayOfWeek) {
            case MONDAY:
                return "월요일";
            case TUESDAY:
                return "화요일";
            case WEDNESDAY:
                return "수요일";
            case THURSDAY:
                return "목요일";
            case FRIDAY:
                return "금요일";
            case SATURDAY:
                return "토요일";
            case SUNDAY:
                return "일요일";
            default:
                return "";
        }
    }

    public String formatMonthList(List<Integer> months, boolean lastDay) {
        if (months == null || months.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder("매월 ");
        for (Integer month : months) {
            sb.append(month).append(", ");
        }

        // 마지막 쉼표와 공백 제거
        sb.setLength(sb.length() - 2);
        sb.append("일");

        if (lastDay) {
            sb.append(", 마지막 날");
        }
        sb.append(" 반복");

        return sb.toString();
    }

}
