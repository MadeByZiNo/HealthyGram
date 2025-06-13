package madebyzino.HealthyGram.infra.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import madebyzino.HealthyGram.infra.security.user.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class UserMetaCheckFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String requestURI    = request.getRequestURI();
        String method = request.getMethod();

        // 웹에서 메타 등록 페이지로 가는 요청(GET /users/meta)은 무조건 통과
        if ("/users/meta".equals(requestURI) && "GET".equals(method)) {
            filterChain.doFilter(request, response);
            return;
        }

        //  API로 메타를 실제 등록·수정하는 요청(POST /api/users/meta)도 무조건 통과
        if ("/api/users/meta".equals(requestURI) && "POST".equals(method)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (requestURI.startsWith("/css/") ||
                requestURI.startsWith("/js/") ||
                requestURI.startsWith("/images/") ||
                requestURI.startsWith("/fonts/")) {
            filterChain.doFilter(request, response);
            return;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() &&
                authentication.getPrincipal() instanceof CustomUserDetails userDetails) {

            // 메타 정보가 없는 사용자라면 /users/meta로 리다이렉트
            if (!userDetails.isMetaRegistered()) {
                response.sendRedirect("/users/meta");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
