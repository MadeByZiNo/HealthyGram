package madebyzino.HealthyGram.domain.feed;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FeedResponse {
    private Long id;
    private Long userId;
    private String nickname;
    private String profileImageUrl;
    private String content;
    private List<String> imageUrls;
    private int likeCount;
    private int commentCount;
    private LocalDateTime createdAt;
}
