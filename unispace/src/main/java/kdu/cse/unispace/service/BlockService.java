package kdu.cse.unispace.service;

import kdu.cse.unispace.exception.block.BlockNotFoundException;
import lombok.RequiredArgsConstructor;
import kdu.cse.unispace.domain.Member;
import kdu.cse.unispace.domain.space.Page;
import kdu.cse.unispace.domain.space.Block;
import kdu.cse.unispace.repository.BlockRepository;
import kdu.cse.unispace.requestdto.space.page.block.BlockUpdateRequestDto;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BlockService {

    private final BlockRepository blockRepository;

//    private final PageRepository pageRepository;
//
//    private final MemberRepository memberRepository;

    private final MemberService memberService;
    private final PageService pageService;

    public Block findOne(Long blockId) {
        return blockRepository.findById(blockId).orElseThrow(()
                -> new BlockNotFoundException("해당하는 블록이 존재하지 않습니다."));
    }

    @Transactional
    public Long makeBlock(Long pageId, Long memberId) { //블럭 생성
        Page page = pageService.findOne(pageId);
        Member member = memberService.findOne(memberId);

        Block block = new Block(page, member);

        blockRepository.save(block);

        return block.getId();
    }

    @Transactional
    public Long updateBlock(Long blockId, BlockUpdateRequestDto requestDto) { //블럭 업데이트
        Block block = findOne(blockId);

//        LocalDateTime now = LocalDateTime.now();

//        block.setUpdatedAt(now);

        Member updatedBy = memberService.findOne(requestDto.getMemberId());
        String content = requestDto.getContent();

        block.update(updatedBy, content);


//
//        if (requestDto.getMemberId() != null) {
//            Member updatedBy = memberService.findOne(requestDto.getMemberId());
//            block.setUpdatedBy(updatedBy);
//        }
//
//
//
//        if (requestDto.getContent() != null) {
//            block.setContent(requestDto.getContent());
//        }

        blockRepository.save(block);

        return block.getId();
    }
    @Transactional
    public void deleteBlock(Long blockId) {
        Block block = findOne(blockId);
        blockRepository.delete(block);
    }


}
