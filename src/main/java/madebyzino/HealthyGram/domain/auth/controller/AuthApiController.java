package madebyzino.HealthyGram.domain.auth.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import madebyzino.HealthyGram.domain.auth.dto.EmailCodeRequest;
import madebyzino.HealthyGram.domain.auth.dto.LoginRequest;
import madebyzino.HealthyGram.domain.auth.dto.RegisterRequest;
import madebyzino.HealthyGram.domain.auth.service.AuthService;
import madebyzino.HealthyGram.domain.auth.service.EmailAuthService;
import madebyzino.HealthyGram.infra.security.jwt.CookieUtil;
import madebyzino.HealthyGram.domain.user.entity.User;
import madebyzino.HealthyGram.domain.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthApiController {

    private final AuthService authService;
    private final EmailAuthService emailAuthService;
    private final UserService userService;
    private final CookieUtil cookieUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request,
                                   HttpServletResponse response) {
        authService.login(request, response);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody RegisterRequest request) {
        User saveUser = userService.register(request);
        return ResponseEntity.ok(saveUser);
    }

    @GetMapping("/email/send-code")
    public ResponseEntity<?> sendCode(@RequestParam String email) {
        emailAuthService.sendAuthCode(email);
        return ResponseEntity.ok().body(Map.of("success", true));
    }

    @PostMapping("/email/verify-code")
    public ResponseEntity<?> verifyCode(@RequestBody EmailCodeRequest request) {
        boolean verified = emailAuthService.verifyCode(request.getEmail(), request.getCode());
        return ResponseEntity.ok().body(Map.of("success", verified));
    }
}
