package kdu.cse.unispace.domain.space.schedule;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import kdu.cse.unispace.domain.space.Space;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule {

    @Id
    @GeneratedValue
    @Column(name = "schedule_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "space_id")
    private Space space;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL)
    private List<Category> categories = new ArrayList<>();

    public Schedule(Space space) {
        this.space = space;
        space.makeRelation(this);
    }

}
