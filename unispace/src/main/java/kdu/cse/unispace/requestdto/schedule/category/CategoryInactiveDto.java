package kdu.cse.unispace.requestdto.schedule.category;

import jakarta.validation.constraints.NotNull;
import kdu.cse.unispace.domain.space.schedule.EndStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryInactiveDto {
    @NotNull(message = "종료 상태가 전달되지 않았습니다.")
    private EndStatus endStatus;
}
