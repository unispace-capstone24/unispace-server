package kdu.cse.unispace.service;

import kdu.cse.unispace.domain.space.Space;
import kdu.cse.unispace.domain.space.TrashCan;
import kdu.cse.unispace.exception.TrashCan.TrashCanNotFoundException;
import kdu.cse.unispace.repository.TrashCanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class TrashCanService {
    private final TrashCanRepository trashCanRepository;

    public TrashCan findOne(Long TrashCanId) {
        return trashCanRepository.findById(TrashCanId).orElseThrow(()
                -> new TrashCanNotFoundException("해당하는 휴지통이 존재하지 않습니다."));
    }
    @Transactional
    public TrashCan makeTrashCan(Space space) {

        TrashCan trashCan = new TrashCan(space);
        trashCanRepository.save(trashCan);

        return trashCan;
    }
}
