package kdu.cse.unispace.responsedto.space.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageBaseResponse {
    private Long id;
    private Integer status;
    private String message;
    public PageBaseResponse(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
