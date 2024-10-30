package kdu.cse.unispace.requestdto.space.page.block;

import lombok.Data;

@Data
public class BlockUpdateRequestDto {
    private Long memberId; //누가 수정했는지 위해서
    private String content;
}
