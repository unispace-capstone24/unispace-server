package kdu.cse.unispace.exception.page;

public class PageRestoreNotCurrentPageIdException extends RuntimeException {
    public PageRestoreNotCurrentPageIdException(String message) {
        super(message);
    }
}
