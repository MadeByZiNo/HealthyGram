package madebyzino.HealthyGram.domain.Exercise.entity;

public enum ExerciseMethod {
    BARBELL("바벨/플레이트"),
    DUMBBELL("덤벨"),
    BODYWEIGHT("맨몸 운동"),
    MACHINE("머신/스미스 머신"),
    CABLE_BAND("케이블/저항 밴드/TRX"),
    BALL("메디신볼/보수볼"),
    KETTLEBELL("케틀벨"),
    CARDIO("유산소"),
    STRETCH_RECOVERY("스트레칭/회복"),
    OTHER("기타");

    private final String koreanName;

    ExerciseMethod(String koreanName) {
        this.koreanName = koreanName;
    }

    public String getKoreanName() {
        return koreanName;
    }
}
