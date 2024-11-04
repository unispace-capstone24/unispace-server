package kdu.cse.unispace.repository;

import kdu.cse.unispace.domain.space.schedule.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
