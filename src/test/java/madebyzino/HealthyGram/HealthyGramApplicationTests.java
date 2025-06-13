package madebyzino.HealthyGram;

import madebyzino.HealthyGram.domain.auth.service.EmailAuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest

class HealthyGramApplicationTests {

	@Autowired
	private EmailAuthService emailAuthService;  // 실제 서비스 빈 주입

	@Autowired
	private RedisTemplate<String, String> redisTemplate; // 실제 RedisTemplate 주입

	private String email;
	private String code;

	@BeforeEach
	void setUp() {
		email = "jh990517@naver.com";
	}

	@Test
	void testSendAuthCode() {
		// 메일을 보내고 코드를 받음
		code = emailAuthService.sendAuthCode(email);

		// Redis에 저장됐는지 확인
		String savedCode = redisTemplate.opsForValue().get("email-auth:" + email);

		assertNotNull(code);
		assertEquals(code, savedCode);
		assertEquals(6, code.length()); // 코드 길이 6자리인지 확인
	}

	@Test
	void testVerifyCode_ValidCode() {
		// 인증코드 발송
		code = emailAuthService.sendAuthCode(email);

		// 올바른 코드로 인증 시도
		boolean verified = emailAuthService.verifyCode(email, code);

		assertTrue(verified);

		// 인증 성공시 verified flag도 Redis에 저장되는지 체크
		String verifiedFlag = redisTemplate.opsForValue().get("email-auth:verified:" + email);
		assertEquals("true", verifiedFlag);
	}

	@Test
	void testVerifyCode_InvalidCode() {
		code = emailAuthService.sendAuthCode(email);

		// 틀린 코드로 인증 시도
		boolean verified = emailAuthService.verifyCode(email, "999999");

		assertFalse(verified);
	}

}
