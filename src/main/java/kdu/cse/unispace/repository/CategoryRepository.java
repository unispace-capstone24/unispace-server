package kdu.cse.unispace.repository;

import kdu.cse.unispace.domain.space.schedule.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
