package madebyzino.HealthyGram.domain.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import madebyzino.HealthyGram.common.BaseEntity;
import madebyzino.HealthyGram.domain.auth.dto.RegisterRequest;
import madebyzino.HealthyGram.domain.user.provider.ProviderInfo;
import madebyzino.HealthyGram.domain.user.usermeta.UserMeta;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = true)
    private String password;

    @Column(nullable = false)
    private UserRole role;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "provider_info_id")
    private ProviderInfo providerInfo;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_meta_id")
    private UserMeta userMeta;

    public static User from(RegisterRequest request) {
        return User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .role(UserRole.NORMAL)
                .build();
    }

    public void attachProviderInfo(ProviderInfo providerInfo) {
        this.providerInfo = providerInfo;
    }

    public void initializeUserMeta(UserMeta userMeta) {
        this.userMeta = userMeta;
    }
}
