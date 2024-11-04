package kdu.cse.unispace.repository;

import kdu.cse.unispace.domain.chat.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByMember1IdOrMember2Id(Long memberId, Long memberId1);

    Room findByMember1IdAndMember2Id(Long memberId, Long friendId);
    Room findByMember2IdAndMember1Id(Long memberId, Long friendId);
    List<Room> findByMember1IdOrMember2IdAndSpaceIsNotNull(Long member1Id, Long member2Id);

    List<Room> findByMember1Id(Long memberId1);

    void delete(Optional<Room> room);
}
