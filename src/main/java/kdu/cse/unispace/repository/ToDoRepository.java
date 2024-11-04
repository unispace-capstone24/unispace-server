package kdu.cse.unispace.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kdu.cse.unispace.domain.space.schedule.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ToDoRepository extends JpaRepository<ToDo, Long> , QuerydslPredicateExecutor<ToDo> {


    List<ToDo> findAllByEasyMake(UUID easyMake);
}
