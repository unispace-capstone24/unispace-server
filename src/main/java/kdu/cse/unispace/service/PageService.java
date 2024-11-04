package kdu.cse.unispace.service;


import kdu.cse.unispace.domain.space.Page;
import kdu.cse.unispace.domain.space.Space;
import kdu.cse.unispace.domain.space.TrashCan;
import kdu.cse.unispace.exception.page.PageDeletionNotAllowedException;
import kdu.cse.unispace.exception.page.PageNotFoundException;
import kdu.cse.unispace.exception.page.PageNotInSpaceException;
import kdu.cse.unispace.exception.page.PageRestoreNotCurrentPageIdException;
import kdu.cse.unispace.repository.PageRepository;
import kdu.cse.unispace.repository.SpaceRepository;
import kdu.cse.unispace.requestdto.space.page.PageCreateRequestDto;
import kdu.cse.unispace.requestdto.space.page.PageUpdateContentRequestDto;
import kdu.cse.unispace.requestdto.space.page.PageUpdateTitleRequestDto;
import kdu.cse.unispace.responsedto.space.page.PageHierarchyDto;
import kdu.cse.unispace.responsedto.space.page.PageTrashCanDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PageService {

    private final PageRepository pageRepository;
    private final SpaceRepository spaceRepository;
    private final SpaceService spaceService;

    public Page findOne(Long pageId) {
        return pageRepository.findById(pageId).orElseThrow(()-> new PageNotFoundException("페이지를 찾을 수 없습니다."));
    }

    public List<PageHierarchyDto> getPageHierarchy(Long pageId) { //계층 조회
        Page page = findOne(pageId);

        List<PageHierarchyDto> pageHierarchy = new ArrayList<>();
        while (page != null) {
            PageHierarchyDto pageDto = new PageHierarchyDto(page.getId(), page.getTitle());
            pageHierarchy.add(pageDto);
            page = page.getParentPage();
        }
        Collections.reverse(pageHierarchy);
        return pageHierarchy;
    }



    @Transactional
    public Long makePage(Long spaceId, PageCreateRequestDto pageCreateRequestDto) {
        Space space = spaceService.findOne(spaceId);
        Page page = new Page(pageCreateRequestDto.getTitle());

        //setPageSpace(page, space);
        page.makeRelationPageSpace(page, space);

        //parent 페이지가 있는 경우
        if (pageCreateRequestDto.getParentPageId() != null && pageCreateRequestDto.getParentPageId().isPresent()) {
            Long parentPageId = pageCreateRequestDto.getParentPageId().orElse(null);
            Page parentPage = pageRepository.findById(parentPageId)
                    .orElseThrow(() -> new PageNotFoundException("부모 페이지 찾기 실패."));
            parentPage.addchildPage(page);


            //pageRepository.save(parentPage);
        }
        else{ //최상위 페이지인 경우
            space.setTopLevelPageCount(space.getTopLevelPageCount()+1);
        }

        pageRepository.save(page);

        return page.getId();
    }


//    @Transactional
//    public void setPageSpace(Page page, Space space) {
//        page.setSpace(space);
//        space.getPageList().add(page);
//    }



    @Transactional
    public void updatePageTitle(Long pageId, PageUpdateTitleRequestDto requestDto) {
        Page page = findOne(pageId);
        page.updatePageTitle(requestDto.getTitle());

//        if (requestDto.getTitle() != null) {
//            page.setTitle(requestDto.getTitle());
//        }
//
//        page.setUpdatedAt(LocalDateTime.now());
    }
    @Transactional
    public void updatePageContent(Long pageId, PageUpdateContentRequestDto requestDto) {
        Page page = findOne(pageId);
        page.updatePageContent(requestDto.getContent());
    }

    @Transactional
    public PageTrashCanDto moveToTrashCan(Page page, Space space) {

        List<PageTrashCanDto> pageTrashCanDtoList = new ArrayList<>();

        //쓰레기통으로 옮기는 작업
        TrashCan trashCan = space.getTrashCan();
        List<Page> pageTrashCanList = trashCan.getPageList();


        if (page.getParentPage() != null) { //부모 페이지가 있다면 연결을 끊어줘야함
            page.removeParentPageRelationWhenThrow();
        }

        PageTrashCanDto pageTrashCanDto = new PageTrashCanDto(page);

        page.putTrashCan(trashCan);//본인 쓰레기통에 넣고


        for (Page chlidPage : page.getChildPages()) {
            chlidPage.putTrashCan(trashCan); //자식페이지들도 쓰레기통에
        }

        return pageTrashCanDto;

    }

    @Transactional
    public PageTrashCanDto throwPage(Long pageId) { //페이지 쓰레기통에 버리기

        Page page = findOne(pageId);
        Optional<Space> optionalSpace = Optional.ofNullable(page.getSpace());
        Space space = optionalSpace.orElseThrow(() -> new PageNotInSpaceException("페이지가 스페이스 내에 없습니다."));

        if (space.getTopLevelPageCount() == 1 && page.getParentPage() == null ) {
            //스페이스에 최상위 페이지가 하나 && 삭제하려는 페이지가 또 제일 최상단 부모페이지면 삭제 불가
            throw new PageDeletionNotAllowedException("최상위 페이지가 하나밖에 없는 경우에는 삭제가 불가능합니다.");
        }

        if (page.getParentPage() == null) { //버리려는 페이지가 최상위 페이지인경우
            space.setTopLevelPageCount(space.getTopLevelPageCount() - 1);
        }

        PageTrashCanDto pageTrashCanDto = moveToTrashCan(page, space);

        System.out.println();


        return pageTrashCanDto;

    }

    @Transactional
    public void restorePageAndChildren(Long pageId, Long spaceId, Long currentPageId) { //페이지 복구
        Page page = findOne(pageId);
        Space space = spaceService.findOne(spaceId);
        TrashCan trashCan = page.getTrashCan();

        if (page.getParentPage() != null) {
            //부모가 있는 페이지인데 특정 자식페이지만 살릴라면 기존 부모페이지와의 연결 끊어주고
            //그리고 현재 보고있는 페이지를 부모 페이지로 해줌
            //이 경우 currentPageId가 반드시 넘어와야함
            if (currentPageId == null) {
                throw new PageRestoreNotCurrentPageIdException("현재 보고 있는 페이지의 Id 정보 - currentPageId 누락");
            }

            Page currentPage = findOne(currentPageId);
            page.removeParentPageRelationWhenRestore(currentPage);

            //page.restoreParentPageRelation(currentPage);

        }

        if (page.getBeforeParentId() != null) { //끊어진 부모페이지와의 관계 이어줌
            Page beforeParent = findOne(page.getBeforeParentId());
            page.outTrashCan(trashCan, beforeParent); // 쓰레기통에서 페이지 제거
        }else{
            page.outTrashCan(trashCan); // 쓰레기통에서 페이지 제거
        }

        //자식 페이지들도 마찬가지
        List<Page> childPages = page.getChildPages();
        for (Page childPage : childPages) {
            childPage.outTrashCan(trashCan); // 쓰레기통에서 페이지 제거
        }

        System.out.println(page.getSpace().getId()+"-----------------");
    }



    @Transactional
    public void deletePage(Long pageId) {
        Page page = findOne(pageId);

        if (page.getTrashCan() == null) { //페이지가 쓰레기통에 있는지 검사
            throw new PageDeletionNotAllowedException("페이지가 쓰레기통에 없습니다.");
        }

        List<Page> childPages = page.getChildPages();
        pageRepository.deleteAll(childPages);
        pageRepository.delete(page);
    }
}
