package kdu.cse.unispace.responsedto.space.page;

import kdu.cse.unispace.domain.space.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDetailDto {
    private Long pageId;
    private String pageTitle;
    private Long spaceId;
    private LocalDateTime createdTime;
    private LocalDateTime lastEditedTime;
    private String content;
    private Optional<Long> parentPageId;
    //private List<BlockDto> blockList;

    public PageDetailDto(Page page) {
        this.pageId = page.getId();
        this.pageTitle = page.getTitle();
        this.spaceId = page.getSpace().getId();
        this.createdTime = page.getCreatedAt();
        this.lastEditedTime = page.getUpdatedAt();
        this.content = page.getContent();
        if (page.getParentPage() != null) {
            this.parentPageId = page.getParentPage().getId().describeConstable();
        }
        //this.blockList = page.getBlockList().stream().map(BlockDto::new).collect(Collectors.toList());

    }

}
