package madebyzino.HealthyGram.domain.user.provider;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity

public class ProviderInfo {

    @Id @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private Provider provider;

    private String providerId;

    public static ProviderInfo of(Provider provider, String providerId) {
        return ProviderInfo.builder()
                .provider(provider)
                .providerId(providerId)
                .build();
    }
}
