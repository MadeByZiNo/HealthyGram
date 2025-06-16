package madebyzino.HealthyGram.domain.Exercise.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
public class Exercise {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    // 운동 부위
    @Enumerated(EnumType.STRING)
    private List<Part> parts;

    // 운동 방법
    @Enumerated(EnumType.STRING)
    private ExerciseMethod exerciseMethod;
}
