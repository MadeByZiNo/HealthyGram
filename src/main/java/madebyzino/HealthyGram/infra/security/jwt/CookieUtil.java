package madebyzino.HealthyGram.infra.security.jwt;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * JWT 토큰을 Cookie로 생성해주는 클래스
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CookieUtil {

    @Value("${jwt.accessTokenExpirationTime}")
    private long accessTokenExpirationTime;

    @Value("${jwt.refreshTokenExpirationTime}")
    private long refreshTokenExpirationTime;

    public void createAccessTokenCookie(HttpServletResponse response, String token) {
        Cookie accessCookie = new Cookie("accessToken", token);
        accessCookie.setHttpOnly(true);
        accessCookie.setSecure(true); // HTTPS 환경에서만 전송
        accessCookie.setPath("/");    // 모든 요청에 전송
        accessCookie.setMaxAge((int) accessTokenExpirationTime);
        response.addCookie(accessCookie);

    }

    public void createRefreshTokenCookie(HttpServletResponse response, String token) {
        Cookie refreshToken = new Cookie("refreshToken", token);
        refreshToken.setHttpOnly(true);
        refreshToken.setSecure(true); // HTTPS 환경에서만 전송
        refreshToken.setPath("/");    // 모든 요청에 전송
        refreshToken.setMaxAge((int) refreshTokenExpirationTime);
        response.addCookie(refreshToken);
    }
}
