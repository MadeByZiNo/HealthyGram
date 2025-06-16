package madebyzino.HealthyGram.domain.feed;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedCreateRequest {
    private String content;
    private Long exerciseSessionId;
}
