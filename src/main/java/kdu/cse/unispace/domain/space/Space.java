package kdu.cse.unispace.domain.space;

import jakarta.persistence.*;
import kdu.cse.unispace.domain.chat.Room;
import kdu.cse.unispace.domain.space.schedule.Schedule;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Space {

    @Id
    @GeneratedValue
    @Column(name = "space_id")
    private Long id;

    @OneToMany(mappedBy = "space", cascade = CascadeType.ALL)
    private List<Page> pageList = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "space"
            , cascade = CascadeType.ALL)
    private Schedule schedule;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "space"
            , cascade = CascadeType.ALL)
    private List<Room> roomList = new ArrayList<>(); //채팅방들
    private int topLevelPageCount = 0;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private TrashCan trashCan;


    public void setTopLevelPageCount(int count) {
        this.topLevelPageCount = count;
    }
    public void makeRelation(Schedule schedule) {
        this.schedule = schedule;
    }

    public void makeTrashCanRelation(TrashCan trashCan) {
        this.trashCan = trashCan;
    }

    public void removePage(Page page) {
        pageList.remove(page);
    }


    public boolean isEmpty() {
        return false;
    }
}
