package kdu.cse.unispace.requestdto.schedule.category;

import kdu.cse.unispace.domain.space.schedule.PublicSetting;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequestDto {
    private String title;
    private PublicSetting publicSetting;
    private String color;

}
