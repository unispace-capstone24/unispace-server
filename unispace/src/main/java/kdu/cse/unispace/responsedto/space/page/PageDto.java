package kdu.cse.unispace.responsedto.space.page;

import kdu.cse.unispace.domain.space.Page;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class PageDto { //얘는 좀 스페이스 이런데서 쓰이는 대략적인 구조만 보여주는 DTO-> BLOCK이 없음
    private Long pageId;
    private String title;
    private Long parentId;
    private List<PageDto> childPageList = new ArrayList<>();


    public PageDto(Page page) {
        this.pageId = page.getId();
        this.title = page.getTitle();
        this.parentId = page.getParentPage() != null ? page.getParentPage().getId() : null;
        this.childPageList = page.getChildPages().stream()
                .filter(childPage -> !childPage.equals(page.getParentPage())) // Filter out parent page
                .map(PageDto::new)
                .collect(Collectors.toList());
    }

    public PageDto(Page page, boolean hasParent) {
        this.pageId = page.getId();
        this.title = page.getTitle();
        if (!hasParent) {
            this.parentId = page.getParentPage() != null ? page.getParentPage().getId() : null;
        }
        this.childPageList = page.getChildPages().stream()
                .filter(childPage -> !childPage.equals(page.getParentPage())) // Filter out parent page
                .map(childPage -> new PageDto(childPage, true))
                .collect(Collectors.toList());
    }

//    public PageDto(Page page) {
//        this.id = page.getId();
//        this.title = page.getTitle();
//        addChildPages(page.getChildPages());
//    }
//
//    private void addChildPages(List<Page> pages) {
//        for (Page page : pages) {
//            if (!childPageList.contains(new PageDto(page))) {
//                PageDto childPage = new PageDto(page);
//                childPageList.add(childPage);
//            }
//        }
//    }
}
