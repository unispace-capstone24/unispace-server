package kdu.cse.unispace.config.jwt;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class SecretKeyGenerator { // 키생성 - 스프링 시큐리티 SecretKeySpec 클래스 사용

    public static SecretKey createSecretKey(String secret, String algorithm) {
        byte[] decodedKey = Base64.getDecoder().decode(secret);
        return new SecretKeySpec(decodedKey, algorithm);
    }

    public static void main(String[] args) {
        String secret = "Qh1bmn/fh/xvrpUVzelQbNeb2J70AqezkyB3+A21aQBXyCAvBQaI9nc+FHRc7dewtfS8szD2tb6ieY41abFifQ==";
        String algorithm = "HmacSHA256";
        SecretKey secretKey = createSecretKey(secret, algorithm);
        System.out.println(secretKey.getEncoded());
    }
}
