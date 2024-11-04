package kdu.cse.unispace.repository;

import kdu.cse.unispace.domain.space.Block;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockRepository<Block> extends JpaRepository<Block, Long> {
}
