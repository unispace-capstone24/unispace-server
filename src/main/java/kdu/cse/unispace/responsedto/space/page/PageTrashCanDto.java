package kdu.cse.unispace.responsedto.space.page;

import kdu.cse.unispace.domain.space.Page;
import kdu.cse.unispace.domain.space.Space;
import kdu.cse.unispace.responsedto.schedule.ScheduleDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageTrashCanDto {
    private Long pageId;
    private String pageTitle;
    private Long parentId;
    private List<PageDto> childPageList = new ArrayList<>();

    public PageTrashCanDto(Page page) {
        this.pageId = page.getId();
        this.pageTitle = page.getTitle();
        this.parentId = page.getParentPage() != null ? page.getParentPage().getId() : null;
        this.childPageList = page.getChildPages().stream()
                .filter(childPage -> !childPage.equals(page.getParentPage()))
                .map(PageDto::new)
                .collect(Collectors.toList());
    }

}
