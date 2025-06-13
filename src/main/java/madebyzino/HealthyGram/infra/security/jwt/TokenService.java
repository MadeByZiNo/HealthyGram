package madebyzino.HealthyGram.infra.security.jwt;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import madebyzino.HealthyGram.domain.auth.dto.LoginResponse;
import madebyzino.HealthyGram.domain.user.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtService jwtService;
    private final CookieUtil cookieUtil;
    private final RedisTemplate<String, String> redisTemplate;

    @Value("${jwt.refreshTokenExpirationTime}")
    private long refreshTokenExpirationTime;

    public LoginResponse generateTokensAndSetCookies(User user, HttpServletResponse response) {

        String accessToken = jwtService.generateAccessToken(
                String.valueOf(user.getId()),
                user.getRole().name(),
                user.getEmail(),
                String.valueOf(user.getUserMeta() != null)
        );

        String refreshToken = jwtService.generateRefreshToken(
                String.valueOf(user.getId()),
                user.getRole().name(),
                user.getEmail(),
                String.valueOf(user.getUserMeta() != null)
        );

        redisTemplate.opsForValue().set("RefreshToken : " + user.getId(), refreshToken, refreshTokenExpirationTime);

        // 쿠키로 세팅
        cookieUtil.createAccessTokenCookie(response, accessToken);
        cookieUtil.createRefreshTokenCookie(response, refreshToken);

        return new LoginResponse(accessToken, refreshToken);
    }
}