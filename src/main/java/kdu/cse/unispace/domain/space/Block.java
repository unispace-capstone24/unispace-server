package kdu.cse.unispace.domain.space;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import kdu.cse.unispace.domain.Member;

import java.time.LocalDateTime;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Block {

    @Id
    @GeneratedValue
    @Column(name = "block_id")
    private Long id;

//    @ManyToOne
//    @JoinColumn(name = "page_id")
//    private Page page;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member createdBy;

    @ManyToOne
    @JoinColumn(name = "updated_by_member_id")
    private Member updatedBy;

    private String content;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Block(Page page, Member member) { //블럭생성
        //this.page = page;
        //page.getBlockList().add(this); 블럭기능 없애면서 에러나서 일단 주석

        LocalDateTime now = LocalDateTime.now();

        createdAt = now;
        updatedAt = now;

        createdBy = member;
        updatedBy = member; //일단 생성시에는 만든사람이 곧 최근에 업데이트한 사람으로
    }

    public void update(Member updatedBy, String content) {

        this.updatedBy = updatedBy;
        this.updatedAt = LocalDateTime.now();
        this.content = content;

//        if (updatedBy != null) {
//            this.updatedBy = updatedBy;
//        }
//        if (updatedAt != null) {
//            this.updatedAt = updatedAt;
//        }
//        if (content != null) {
//            this.content = content;
//        }
    }


}
