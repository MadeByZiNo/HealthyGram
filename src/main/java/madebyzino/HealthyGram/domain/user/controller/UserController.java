package madebyzino.HealthyGram.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    @GetMapping("/meta")
    public String initializeUserMetaPage() {
        return "user/meta_initialize";
    }
}
