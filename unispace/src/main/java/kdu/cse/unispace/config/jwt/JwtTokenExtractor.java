package kdu.cse.unispace.config.jwt;

import jakarta.servlet.http.HttpServletRequest;

public class JwtTokenExtractor {  
    public static final String HEADER_PREFIX = "Bearer ";

    public static String extract(HttpServletRequest request) {
        String header = request.getHeader("JWT-Authorization");

        if (header == null || header.isEmpty() || !header.startsWith(HEADER_PREFIX)) {
            return null;
        }

        return header.substring(HEADER_PREFIX.length());
    }
}
