package madebyzino.HealthyGram.domain.Exercise.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import madebyzino.HealthyGram.common.BaseEntity;
import madebyzino.HealthyGram.domain.feed.Feed;
import madebyzino.HealthyGram.domain.user.entity.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
public class ExerciseSession extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private LocalDateTime performedAt;

    @OneToMany(mappedBy = "exerciseSession", cascade = CascadeType.ALL)
    private List<ExerciseSet> sets = new ArrayList<>();

    @OneToOne(mappedBy = "exerciseSession", cascade = CascadeType.ALL)
    private Feed feed;
}
