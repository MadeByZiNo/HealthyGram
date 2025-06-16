package madebyzino.HealthyGram.image;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import madebyzino.HealthyGram.domain.feed.Feed;

@Entity
@NoArgsConstructor
public class FeedImage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "feed_id")
    private Feed feed;

    @Column(name = "name", nullable = false)
    private String url;
}
