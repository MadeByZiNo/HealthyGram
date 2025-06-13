package madebyzino.HealthyGram.domain.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/register/start")
    public String registerStartPage() {
        return "auth/register_start";
    }

    @GetMapping("/register")
    public String registerLocalPage() {
        return "auth/register_local";
    }
}
