package kdu.cse.unispace.requestdto.schedule.todo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class ChangeToDoRequestDto {
    @NotNull(message = "변경 날짜가 누락되었습니다.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime changeDay;
}
