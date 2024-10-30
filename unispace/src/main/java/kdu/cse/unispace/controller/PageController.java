package kdu.cse.unispace.controller;

import kdu.cse.unispace.config.jwt.JwtAuthenticationFilter;
import kdu.cse.unispace.domain.Member;
import kdu.cse.unispace.domain.space.Block;
import kdu.cse.unispace.domain.space.Page;
import kdu.cse.unispace.requestdto.space.page.PageUpdateContentRequestDto;
import kdu.cse.unispace.requestdto.space.page.PageUpdateTitleRequestDto;
import kdu.cse.unispace.requestdto.space.page.block.BlockCreateRequestDto;
import kdu.cse.unispace.requestdto.space.page.block.BlockUpdateRequestDto;
import kdu.cse.unispace.responsedto.BasicResponse;
import kdu.cse.unispace.responsedto.space.page.*;
import kdu.cse.unispace.responsedto.space.page.block.BlockDto;
import kdu.cse.unispace.service.BlockService;
import kdu.cse.unispace.service.MemberService;
import kdu.cse.unispace.service.PageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class PageController {
    private static final int SUCCESS = 200;
    private static final int CREATED = 201;
    private final PageService pageService;
    private final BlockService blockService;
    private final MemberService memberService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    //페이지 생성은 SpaceController에서

    @GetMapping("/page/{pageId}") //페이지 조회
    //@PreAuthorize("@customSecurityUtil.isPageOwner(#pageId)")
    public ResponseEntity<PageDetailDto> getPage(@PathVariable Long pageId, HttpServletRequest request) {
        jwtAuthenticationFilter.isPageOwner(request, pageId); //접근권한 확인
        Page page = pageService.findOne(pageId);
        PageDetailDto pageDetailDto = new PageDetailDto(page);

        return ResponseEntity.ok(pageDetailDto);
    }

    @GetMapping("/page/{pageId}/hierarchy") //페이지 계층 조회
    //@PreAuthorize("@customSecurityUtil.isPageOwner(#pageId)")
    public ResponseEntity<List<PageHierarchyDto>> getPageHierarchy(@PathVariable Long pageId,
                                                                   HttpServletRequest request) {
        jwtAuthenticationFilter.isPageOwner(request, pageId); //접근권한 확인

        List<PageHierarchyDto> pageHierarchy = pageService.getPageHierarchy(pageId);

        return new ResponseEntity<>(pageHierarchy, HttpStatus.OK);
    }

    @PatchMapping("/page/{pageId}/title")  //페이지 제목 업데이트
    //@PreAuthorize("@customSecurityUtil.isPageOwner(#pageId)")
    public ResponseEntity<PageBaseResponse> updatePageTitle(@PathVariable Long pageId,
                                                            @RequestBody PageUpdateTitleRequestDto requestDto,
                                                            HttpServletRequest request) {
        jwtAuthenticationFilter.isPageOwner(request, pageId); //접근권한 확인

        pageService.updatePageTitle(pageId, requestDto);

        PageBaseResponse pageBaseResponse = new PageBaseResponse(pageId, SUCCESS, "페이지 제목 변경 완료");

        return new ResponseEntity<>(pageBaseResponse, HttpStatus.OK);
    }

    @PatchMapping("/page/{pageId}/content")  //페이지 내용 업데이트
    //@PreAuthorize("@customSecurityUtil.isPageOwner(#pageId)")
    public ResponseEntity<PageBaseResponse> updatePageContent(@PathVariable Long pageId,
                                                              @RequestBody PageUpdateContentRequestDto requestDto,
                                                              HttpServletRequest request) {
        jwtAuthenticationFilter.isPageOwner(request, pageId); //접근권한 확인

        pageService.updatePageContent(pageId, requestDto);

        PageBaseResponse pageBaseResponse = new PageBaseResponse(pageId, SUCCESS, "페이지 내용 변경 완료");

        return new ResponseEntity<>(pageBaseResponse, HttpStatus.OK);
    }

    @PatchMapping("/page/{pageId}/trashcan") //페이지 휴지통 이동
    @PreAuthorize("@jwtAuthenticationFilter.isPageOwner(#request, #pageId)")
    public ResponseEntity<BasicResponse> throwPage(@PathVariable Long pageId, HttpServletRequest request) {
        jwtAuthenticationFilter.isPageOwner(request, pageId); //접근권한 확인
        //List<PageTrashCanDto> pageTrashCanDtoList = pageService.throwPage(pageId);
        PageTrashCanDto pageTrashCanDto = pageService.throwPage(pageId);
        Page page = pageService.findOne(pageId);
        BasicResponse basicResponse = new BasicResponse<>(1, "페이지 휴지통 이동 성공",pageTrashCanDto);
        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
    }


    @DeleteMapping("/page/{pageId}/trashcan") // (쓰레기통에 있는) 페이지 삭제
    @PreAuthorize("@jwtAuthenticationFilter.isPageOwner(#request, #pageId)")
    public ResponseEntity<BasicResponse> deletePage(@PathVariable Long pageId, HttpServletRequest request) {
        jwtAuthenticationFilter.isPageOwner(request, pageId); //접근권한 확인
        pageService.deletePage(pageId);
        BasicResponse basicResponse
                = new BasicResponse(1, "페이지 삭제 완료", null);
        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
    }



//    @PostMapping("/page/{pageId}/block") //블록 생성
//    @PreAuthorize("@jwtAuthenticationFilter.isPageOwner(#request, #pageId)")
//    public ResponseEntity<BasicResponse> createBlock(@PathVariable Long pageId,
//                                                     @RequestBody BlockCreateRequestDto blockCreateRequestDto,
//                                                     HttpServletRequest request) {
//        Long memberId = blockCreateRequestDto.getMemberId();
//        Long blockId = blockService.makeBlock(pageId, memberId);
//        Block block = blockService.findOne(blockId);
//
//        BasicResponse basicResponse = new BasicResponse(1, "블럭 생성 성공", new BlockDto(block));
//
//        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
//    }

//    @PatchMapping("/block/{blockId}") //블럭 업데이트
//    @PreAuthorize("@customSecurityUtil.isBlockOwner(#blockId)")
//    public ResponseEntity<BasicResponse> updateBlock(@PathVariable Long blockId, @RequestBody BlockUpdateRequestDto requestDto) {
////        Optional<Block> optionalBeforeBlock = blockService.findOne(blockId);
////        Block beforUpdateBlock = optionalBeforeBlock.orElseThrow(() -> new EntityNotFoundException("블럭을 찾을 수 없습니다. blockId: " + blockId));
//
//        Member member = memberService.findOne(requestDto.getMemberId());
//
//        // 업데이트
//        Long updateBlockId = blockService.updateBlock(blockId, requestDto);
//        Block block = blockService.findOne(updateBlockId);
////        Optional<Block> optionalBlock = blockService.findOne(blockId);
////        Block block = optionalBlock.orElseThrow(() -> new EntityNotFoundException("블럭을 찾을 수 없습니다. blockId: " + blockId));
//
//        BasicResponse basicResponse = new BasicResponse(1, "블럭 업데이트 성공", new BlockDto(block));
//
//        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
//    }
//
//
//
//    @DeleteMapping("/block/{blockId}") //블록 삭제
//    @PreAuthorize("@customSecurityUtil.isBlockOwner(#blockId)")
//    public ResponseEntity<BasicResponse> deleteBlock(@PathVariable Long blockId) {
//        blockService.deleteBlock(blockId);
//
//        BasicResponse basicResponse = new BasicResponse(1, "블럭 삭제 성공", null);
//        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
//    }


}
