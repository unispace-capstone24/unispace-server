package kdu.cse.unispace.domain.space.schedule;

import kdu.cse.unispace.requestdto.schedule.category.CategoryInactiveDto;
import kdu.cse.unispace.requestdto.schedule.category.CategoryRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<ToDo> todoList = new ArrayList<>();

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<EasyToDo> easyToDoList = new ArrayList<>();

    private String title; //카테고리 제목
    @Enumerated(EnumType.STRING)
    private PublicSetting publicSetting;
    private String color;
    private boolean end;
    @Enumerated(EnumType.STRING)
    private EndStatus endStatus;



//    @ElementCollection
//    @Column(name = "easyToDo")
//    private List<UUID> easyToDo;

    public Category(Schedule schedule, CategoryRequestDto categoryRequestDto) {
        this.schedule = schedule;
        this.title = categoryRequestDto.getTitle();
        this.publicSetting = categoryRequestDto.getPublicSetting();
        this.color = categoryRequestDto.getColor();
        this.end = false;
        this.endStatus = null;
    }

    public Category(Optional<Schedule> schedule, CategoryRequestDto categoryRequestDto) {
    }

    public void changeTitle(String title) {
        this.title = title;
    }
    public void changePublicSetting(PublicSetting publicSetting) {
        this.publicSetting = publicSetting;
    }
    public void changeColor(String color) {
        this.color = color;
    }
    public void changeToInActive(CategoryInactiveDto categoryInactiveDto) {
        this.end = true;
        this.endStatus = categoryInactiveDto.getEndStatus();
    }
    public void changeToActive() {
        this.end = false;
        this.endStatus = null;
    }
}
