package madebyzino.HealthyGram.infra.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import madebyzino.HealthyGram.infra.security.user.CustomUserDetails;
import madebyzino.HealthyGram.infra.security.jwt.JwtService;
import madebyzino.HealthyGram.domain.user.entity.UserRole;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 토큰을 가져온다.
        String accessToken = extractCookie(request, "accessToken");

        if (accessToken != null) {
            // 토큰이 만료되지 않았다면 유저 인증객체를 시큐리티에 등록
            if (jwtService.isValidAccessToken(accessToken)) {
                authenticateUser(accessToken);
            } else {
                // 액세스 토큰 만료 : 리프레시 토큰으로 재발급 시도

                String refreshToken = extractCookie(request, "refreshToken");

                if (refreshToken != null && jwtService.isValidRefreshToken(refreshToken)) {
                    String newAccessToken = jwtService.generateAccessToken(jwtService.getUserId(refreshToken), jwtService.getRole(refreshToken), jwtService.getEmail(refreshToken), jwtService.getIsMetaRegistered(refreshToken) );
                    // 새 AccessToken 쿠키로 덮어쓰기
                    Cookie newAccess = new Cookie("accessToken", newAccessToken);
                    newAccess.setHttpOnly(true);
                    newAccess.setSecure(true);
                    newAccess.setPath("/");
                    newAccess.setMaxAge(30 * 60);
                    response.addCookie(newAccess);

                    authenticateUser(newAccessToken);
                }
                // 둘 다 만료됐으면 그냥 다음 필터로 넘어가게 처리 (오류)
            }
        }
        filterChain.doFilter(request, response);
    }

    private void authenticateUser (String token){
        String userId = jwtService.getUserId(token);
        String email = jwtService.getEmail(token);
        String role = jwtService.getRole(token);
        String isMetaRegistered = jwtService.getIsMetaRegistered(token);

        CustomUserDetails userDetails = new CustomUserDetails(userId, email, UserRole.valueOf(role), Boolean.valueOf(isMetaRegistered));

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


    private String extractCookie(HttpServletRequest request, String name) {
        if (request.getCookies() == null) return null;
        for (Cookie cookie : request.getCookies()) {
            if (name.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
}

