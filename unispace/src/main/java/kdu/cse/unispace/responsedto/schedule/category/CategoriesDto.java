package kdu.cse.unispace.responsedto.schedule.category;

import kdu.cse.unispace.domain.space.schedule.Category;
import kdu.cse.unispace.domain.space.schedule.EndStatus;
import kdu.cse.unispace.domain.space.schedule.PublicSetting;
import lombok.Data;

@Data
public class CategoriesDto { //카테고리들만 간단히 보여주는 용도
    private Long categoryId;
    private String title;
    private PublicSetting publicSetting;
    private String color;
    private boolean end;
    private EndStatus endStatus;

    public CategoriesDto(Category category) {
        this.categoryId = category.getId();
        this.title = category.getTitle();
        this.publicSetting = category.getPublicSetting();
        this.color = category.getColor();
        this.end = category.isEnd();
        this.endStatus = category.getEndStatus();
    }
}
