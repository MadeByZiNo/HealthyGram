package madebyzino.HealthyGram.domain.feed;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import madebyzino.HealthyGram.common.BaseEntity;
import madebyzino.HealthyGram.domain.Exercise.entity.ExerciseSession;
import madebyzino.HealthyGram.domain.user.entity.User;
import madebyzino.HealthyGram.image.FeedImage;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Feed  extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, name = "content")
    private String content;

    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FeedImage> feedImage  = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "exercise_session_id")
    private ExerciseSession exerciseSession;

    // 댓글, 좋아요, 이미지, 해시태그 등 추가


    public static Feed from(FeedCreateRequest request, User user, ExerciseSession exerciseSession) {
        return Feed.builder()
                .content(request.getContent())
                .user(user)
                .exerciseSession(exerciseSession) // null 허용 시 선택
                .build();
    }
}
