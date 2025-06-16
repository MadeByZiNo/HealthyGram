package madebyzino.HealthyGram.domain.user.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import madebyzino.HealthyGram.domain.user.dto.UserMetaInitializeRequest;
import madebyzino.HealthyGram.domain.user.service.UserService;
import madebyzino.HealthyGram.domain.user.usermeta.UserMeta;
import madebyzino.HealthyGram.infra.security.jwt.CookieUtil;
import madebyzino.HealthyGram.infra.security.user.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/users")
@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;

    @PostMapping("/meta")
    public ResponseEntity<UserMeta> initializeUserMeta(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                       @ModelAttribute(value = "request")  UserMetaInitializeRequest request,
                                                       @RequestPart(value = "profileImage", required = false) MultipartFile profileImage,
                                                       HttpServletResponse response) {
        UserMeta userMeta = userService.initializeUserMeta(request, profileImage, Long.valueOf(userDetails.getId()), response);
        return ResponseEntity.ok(userMeta);
    }
}
