package kdu.cse.unispace.responsedto.space;

import kdu.cse.unispace.domain.Member;
import kdu.cse.unispace.domain.space.Space;
import kdu.cse.unispace.responsedto.schedule.ScheduleDto;
import kdu.cse.unispace.responsedto.space.page.PageDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class SpaceDto {

    private Long spaceId;
    private String type;

    private ScheduleDto schedule;

    private List<PageDto> pageList = new ArrayList<>();


//    public SpaceDto(Space space) {
//        this.spaceId = space.getId();
//        this.type = space.getClass().getSimpleName();
//        pageList = space.getPageList().stream().map(PageDto::new).collect(Collectors.toList());
//    }

//    public SpaceDto(Space space) {
//        this.spaceId = space.getId();
//        this.type = space.getClass().getSimpleName();
//        this.schedule = new ScheduleDto(space.getSchedule());
//        pageList = space.getPageList().stream()
//                .map(page -> new PageDto(page, false))//제일 상위의 페이지만 dto로
//                .filter(page -> page.getParentId() == null)
//                .collect(Collectors.toList());
//    }

    public SpaceDto(Space space) {
        this.spaceId = space.getId();
        this.type = space.getClass().getSimpleName();
        this.schedule = new ScheduleDto(space.getSchedule());
        pageList = space.getPageList().stream()
                .filter(page -> page.getParentPage() == null)
                .map(page -> new PageDto(page))
                .collect(Collectors.toList());
    }


}
