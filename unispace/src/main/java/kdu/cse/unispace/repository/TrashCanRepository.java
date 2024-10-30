package kdu.cse.unispace.repository;

import kdu.cse.unispace.domain.space.TrashCan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrashCanRepository  extends JpaRepository<TrashCan, Long> {
}
