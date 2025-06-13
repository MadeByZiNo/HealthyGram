package madebyzino.HealthyGram.domain.auth.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import madebyzino.HealthyGram.common.exception.CustomException;
import madebyzino.HealthyGram.common.exception.ErrorCode;
import madebyzino.HealthyGram.domain.auth.dto.LoginRequest;
import madebyzino.HealthyGram.domain.user.entity.User;
import madebyzino.HealthyGram.domain.user.service.UserService;
import madebyzino.HealthyGram.infra.security.jwt.JwtService;
import madebyzino.HealthyGram.infra.security.jwt.TokenService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    private final UserService userService;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;


    public void login(LoginRequest request, HttpServletResponse response) {

        User user = userService.findByEmail(request.getEmail());

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_MISMATCH);
        }

        tokenService.generateTokensAndSetCookies(user, response);
    }
}