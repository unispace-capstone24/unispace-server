package kdu.cse.unispace.repository;

import kdu.cse.unispace.domain.space.schedule.EasyToDo;
import kdu.cse.unispace.domain.space.schedule.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EasyToDoRepository extends JpaRepository<EasyToDo, Long> {
    Optional<EasyToDo> findByEasyMake(UUID easyToDoId);
}
