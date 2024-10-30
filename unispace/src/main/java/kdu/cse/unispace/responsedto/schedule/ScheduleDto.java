package kdu.cse.unispace.responsedto.schedule;

import kdu.cse.unispace.domain.space.schedule.Schedule;
import kdu.cse.unispace.responsedto.schedule.category.CategoryDto;
import kdu.cse.unispace.domain.space.schedule.Schedule;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ScheduleDto {
    private Long scheduleId;
    private Long spaceId;
    private int categoryCount;

    private List<CategoryDto> categories;

    public ScheduleDto(Schedule schedule) {
        scheduleId = schedule.getId();
        spaceId = schedule.getSpace().getId();
        categories = schedule.getCategories().stream()
                .map(category -> new CategoryDto(category))
                .collect(Collectors.toList());
        categoryCount = categories.size();
    }
}
