package kdu.cse.unispace.requestdto.schedule.category;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryWeeklyEasyDto {
    @NotNull(message = "제목이 누락되었습니다.")
    private String description;
    @NotNull(message = "시작 날짜가 누락되었습니다.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startDay;
    @NotNull(message = "종료 날짜가 누락되었습니다.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endDay;

    @NotNull(message = "요일이 누락되었습니다.")
    private DayOfWeek[] week;

}
