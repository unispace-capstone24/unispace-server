package kdu.cse.unispace.exception.jwt;

public class TokenInvalidateException extends RuntimeException {
    public TokenInvalidateException  (String message) {
        super(message);
    }
}
