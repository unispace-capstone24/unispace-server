package kdu.cse.unispace.repository;

import kdu.cse.unispace.domain.space.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PageRepository extends JpaRepository<Page, Long> , QuerydslPredicateExecutor<Page> {
}

