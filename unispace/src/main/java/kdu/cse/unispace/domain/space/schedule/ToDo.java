package kdu.cse.unispace.domain.space.schedule;


import jakarta.persistence.*;
import kdu.cse.unispace.exception.todo.ToDoActiveException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ToDo {

    @Id
    @GeneratedValue
    @Column(name = "todo_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "easy_category_id")
//    private Category easyCategory;

    private String description;
    private Boolean completed;
    private LocalDateTime date; //수행해야할 날짜
    private boolean active;

    /**
     * 간편입력용
     */

    private UUID easyMake; //간편등록시에만 사용됨

    private LocalDateTime start; //간편등록시에만 사용됨

    private LocalDateTime end; //간편등록시에만 사용됨



    public ToDo(Category category, String description, Boolean completed,
                LocalDateTime date, boolean active, UUID uuid) {
        this.category = category;
        this.description = description;
        this.completed = completed;
        this.date = date;
        this.active = active;
        this.easyMake = uuid;
    }

    public ToDo(Optional<Category> findCategory, String description, Boolean completed, LocalDateTime now, boolean active, Object uuid) {
    }


    public void changeDescription(String description) {
        this.description = description;
    }

    public void updateCompleted(Boolean completed) {
        this.completed = completed;
    }

    public void changeActive() {
        if (this.active) {
            throw new ToDoActiveException("이미 활성화중인 투두입니다.");
        }
        easyMake = null;
        this.active = true;
    }

    public void changeDate(LocalDateTime start, LocalDateTime end ) {
        this.start = start;
        this.end = end;
    }

    public void doToday(LocalDateTime time) {
        this.date = time;
    }
}
