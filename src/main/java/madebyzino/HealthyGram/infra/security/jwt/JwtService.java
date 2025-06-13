package madebyzino.HealthyGram.infra.security.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtService {

    private final JWTUtil jwtUtil;

    public String generateAccessToken(String userId, String role, String email, String isMetaRegistered) {
        return jwtUtil.generateAccessToken(userId, role, email, isMetaRegistered);
    }

    public String generateRefreshToken(String userId, String role, String email, String isMetaRegistered) {
        return jwtUtil.generateRefreshToken(userId, role, email, isMetaRegistered);
    }

    public boolean isValidAccessToken(String accessToken) {
        return jwtUtil.validateToken(accessToken);
    }

    public boolean isValidRefreshToken(String refreshToken) {
        return jwtUtil.validateToken(refreshToken);
    }

    public String getUserId(String token) {
        return jwtUtil.getUserId(token);
    }

    public String getEmail(String token) {
        return jwtUtil.getEmail(token);
    }

    public String getRole(String token) {
        return jwtUtil.getRole(token);
    }

    public String getIsMetaRegistered(String token) {
        return jwtUtil.getIsMetaRegistered(token);
    }
}
