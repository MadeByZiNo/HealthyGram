package madebyzino.HealthyGram.domain.Exercise.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import madebyzino.HealthyGram.common.BaseEntity;

@Entity
@NoArgsConstructor
public class ExerciseSet  {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ExerciseSession exerciseSession;

    @ManyToOne
    private Exercise exercise;

    private int reps;

    private Double weight;

    private boolean weightPrivate;
}
