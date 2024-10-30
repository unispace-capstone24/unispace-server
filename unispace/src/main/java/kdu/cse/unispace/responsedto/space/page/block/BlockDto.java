package kdu.cse.unispace.responsedto.space.page.block;

import kdu.cse.unispace.domain.space.Block;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlockDto {
    //private int blockCount;
    //private blocks;
    private Long blockId;
    private Long pageId;
    private LocalDateTime createdTime;
    private LocalDateTime lastEditedTime;
    private String createdBy;
    private String lastEditedBy;
    private String content;


    public BlockDto(Block block) {
        this.blockId = block.getId();
        //this.pageId = block.getPage().getId();
        this.createdTime = block.getCreatedAt();
        this.lastEditedTime = block.getUpdatedAt();
        this.createdBy = block.getCreatedBy().getMemberName();
        this.lastEditedBy = block.getUpdatedBy().getMemberName();
        this.content = block.getContent();
    }

}
