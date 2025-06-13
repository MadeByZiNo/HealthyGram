package madebyzino.HealthyGram.domain.auth.service;

import lombok.RequiredArgsConstructor;
import madebyzino.HealthyGram.infra.email.AuthCodeEmailForm;
import madebyzino.HealthyGram.infra.email.EmailSender;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class EmailAuthService {

    private final EmailSender emailSender;
    private final RedisTemplate<String, String> redisTemplate;

    public String sendAuthCode(String email) {

        String code = generateCode();
        redisTemplate.opsForValue().set("email-auth:" + email, code, Duration.ofMinutes(3));
        emailSender.send(new AuthCodeEmailForm(email, code));
        return code;
    }

    public boolean verifyCode(String email, String inputCode) {

        String key = "email-auth:" + email;
        String savedCode = redisTemplate.opsForValue().get(key);

        if(inputCode.equals(savedCode)){
            redisTemplate.opsForValue().set("email-auth:verified:" + email, "true", Duration.ofMinutes(10));
            return true;
        }
        return false;
    }


    private String generateCode() {
        return String.valueOf((int)(Math.random() * 900000) + 100000);
    }

}
