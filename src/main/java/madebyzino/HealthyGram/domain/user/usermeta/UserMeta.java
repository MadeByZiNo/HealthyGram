package madebyzino.HealthyGram.domain.user.usermeta;

import jakarta.persistence.*;
import lombok.*;
import madebyzino.HealthyGram.domain.user.dto.UserMetaInitializeRequest;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserMeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String nickname;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = true, length = 200)
    private String bio;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = true)
    private String profileImageUrl;

    @Column(nullable = true)
    private LocalDate birthDate;

    public static UserMeta from(UserMetaInitializeRequest request) {
        return UserMeta.builder()
                .bio(request.getBio())
                .nickname(request.getNickname())
                .name(request.getName())
                .gender(request.getGender())
                .birthDate(request.getBirthDate())
                .build();
    }

    public void updateProfileImageUrl(String profileImageUrl){
        this.profileImageUrl = profileImageUrl;
    }
}