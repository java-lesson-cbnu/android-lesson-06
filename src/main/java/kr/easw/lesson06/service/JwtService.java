package kr.easw.lesson06.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Random;

@Service
public class JwtService {
    // 무작위 문자열에 사용될 문자열 셋입니다.
    private static final String STRING_COLLECTION = "0123456789abcdefghijklmnopqrstuvwxyz_+-=~";

    // 무작위 문자열을 생성할 랜덤 객체입니다.
    private static final Random RANDOM = new Random();

    // 초기 JWT 시크릿 키입니다.
    // 이는 어플리케이션 실행마다 초기화됩니다.
    // 초기화 후에는 이전에 생성된 토큰이 유효하지 않습니다.
    private final SecretKey initialJwtSecret = Keys.hmacShaKeyFor(generateRandomString(64).getBytes(StandardCharsets.UTF_8));

    // JWT 파서입니다.
    private final JwtParser jwtParser = Jwts.parser().verifyWith(initialJwtSecret).build();

    // JWT 만료 시간입니다.
    private final int jwtExpire = 60 * 60 * 1000;

    // 무작위 문자열을 생성합니다.
    private String generateRandomString(int length) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(STRING_COLLECTION.charAt(RANDOM.nextInt(STRING_COLLECTION.length())));
        }
        return builder.toString();
    }

    // JWT 토큰을 생성합니다.
    public String generateToken(String user) {
        // JWT 토큰을 생성합니다.
        return Jwts.builder()
                .subject(user)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpire))
                .signWith(initialJwtSecret, Jwts.SIG.HS512)
                .compact();
    }

    // JWT 토큰을 검증합니다.
    public ValidateStatus validate(String token) {
        try {
            // JWT 토큰을 검증합니다.
            jwtParser.parseSignedClaims(token).getPayload();
            // 검증에 성공하면 VALID를 반환합니다.
            return ValidateStatus.VALID;
        } catch (ExpiredJwtException ex) {
            // 만약 JWT 토큰이 만료되었다면 EXPIRED를 반환합니다.
            return ValidateStatus.EXPIRED;
        } catch (UnsupportedJwtException ex) {
            // 만약 JWT 토큰이 지원되지 않는다면 UNSUPPORTED를 반환합니다.
            return ValidateStatus.UNSUPPORTED;
        } catch (Exception ex) {
            // 만약 JWT 토큰이 유효하지 않다면 INVALID를 반환합니다.
            return ValidateStatus.INVALID;
        }
    }

    // JWT 토큰에서 유저 이름을 추출합니다.
    public String extractUsername(String token) {
        return jwtParser.parseSignedClaims(token).getPayload().getSubject();
    }

    @Getter
    @RequiredArgsConstructor
    public enum ValidateStatus {
        VALID(true), INVALID(false), EXPIRED(false), UNSUPPORTED(false);
        private final boolean valid;

    }

}
