package kdu.cse.unispace.domain.space;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TrashCan {
    @Id
    @GeneratedValue
    @Column(name = "trashcan_id")
    private Long id;

    @OneToOne(mappedBy = "trashCan", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Space space;

    @OneToMany(mappedBy = "trashCan")
    private List<Page> pageList = new ArrayList<>();

    public TrashCan(Space space) {
        this.space = space;
    }

    public void removePage(Page page) {
        pageList.remove(page);
        page.outTrashCan(this);
    }


}
