package madebyzino.HealthyGram.domain.user.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import madebyzino.HealthyGram.common.S3Uploader;
import madebyzino.HealthyGram.common.exception.CustomException;
import madebyzino.HealthyGram.common.exception.ErrorCode;
import madebyzino.HealthyGram.common.exception.UploadDirectory;
import madebyzino.HealthyGram.domain.auth.dto.RegisterRequest;
import madebyzino.HealthyGram.domain.user.provider.Provider;
import madebyzino.HealthyGram.domain.user.provider.ProviderInfo;
import madebyzino.HealthyGram.domain.user.usermeta.UserMeta;
import madebyzino.HealthyGram.domain.user.usermeta.UserMetaJpaRepository;
import madebyzino.HealthyGram.domain.user.entity.User;
import madebyzino.HealthyGram.domain.user.repository.UserJpaRepository;
import madebyzino.HealthyGram.infra.security.jwt.JwtService;
import madebyzino.HealthyGram.infra.security.jwt.TokenService;
import madebyzino.HealthyGram.domain.user.dto.UserMetaInitializeRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserJpaRepository userJpaRepository;
    private final UserMetaJpaRepository userMetaJpaRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final TokenService tokenService;
    private final JwtService jwtService;
    private final S3Uploader s3Uploader;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.refreshTokenExpirationTime}")
    private long refreshTokenExpirationTime;

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userJpaRepository.findById(id)
                .orElseThrow(()->new CustomException(ErrorCode.INVALID_USER));
    }


    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userJpaRepository.findByEmail(email)
                .orElseThrow(()->new CustomException(ErrorCode.INVALID_USER));
    }

    @Transactional
    public User register(RegisterRequest request) {

        // 이메일 패턴 위반 체크
        if (!isValidEmail(request.getEmail())) {
            throw new CustomException(ErrorCode.EMAIL_INVALID_FORMAT);
        }

        // 비밀번호 확인
        if (!isValidPassword(request.getPassword(), request.getConfirmPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_MISMATCH);
        }

        // 이메일 중복 체크
        if (userJpaRepository.existsByEmail(request.getEmail())) {
            throw new CustomException(ErrorCode.EMAIL_DUPLICATE);
        }

        request.setPassword(passwordEncoder.encode(request.getPassword()));

        User newUser = User.from(request);
        userJpaRepository.save(newUser);

        ProviderInfo providerInfo = ProviderInfo.of(Provider.LOCAL, String.valueOf(newUser.getId()));
        newUser.attachProviderInfo(providerInfo);

        return newUser;
    }

    @Transactional
    public UserMeta initializeUserMeta(UserMetaInitializeRequest request, MultipartFile profileImage, Long userId, HttpServletResponse response) {

        // 닉네임 중복 체크
        if (userMetaJpaRepository.existsByNickname(request.getNickname())) {
            throw new CustomException(ErrorCode.NICKNAME_DUPLICATE);
        }

        User user = userJpaRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_USER));
        UserMeta userMeta = UserMeta.from(request);

        // 프로필 사진 업로드
        String url = profileImage.isEmpty() ? null : s3Uploader.upload(profileImage, UploadDirectory.PROFILE_IMAGE.getValue());
        userMeta.updateProfileImageUrl(url);

        //유저에 정보 적용
        user.initializeUserMeta(userMeta);

        //토큰 재발급
        tokenService.generateTokensAndSetCookies(user, response);

        return userMeta;
    }


    private boolean isValidEmail(String email) {
        String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email != null && email.matches(emailPattern);
    }

    private boolean isValidPassword(String password, String passwordConfirm) {
        return password != null && passwordConfirm != null && password.equals(passwordConfirm);
    }
}
