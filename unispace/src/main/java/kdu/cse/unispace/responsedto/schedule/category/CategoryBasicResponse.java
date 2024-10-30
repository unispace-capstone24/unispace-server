package kdu.cse.unispace.responsedto.schedule.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryBasicResponse {
    private Long id;
    private Integer status;
    private String message;

    public CategoryBasicResponse(Integer status, String message) {
        this.status = status;
        this.message = message;
    }


}
