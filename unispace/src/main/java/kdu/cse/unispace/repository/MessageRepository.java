package kdu.cse.unispace.repository;

import kdu.cse.unispace.domain.chat.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
