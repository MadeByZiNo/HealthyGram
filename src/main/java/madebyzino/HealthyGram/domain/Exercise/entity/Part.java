package madebyzino.HealthyGram.domain.Exercise.entity;

public enum Part {
    CHEST("가슴"),
    BACK("등"),
    SHOULDERS("어깨"),
    TRICEPS("삼두"),
    BICEPS("이두"),
    FOREARMS("전완"),
    ABS("복근"),
    GLUTES("둔근"),
    HAMSTRINGS("햄스트링"),
    QUADRICEPS("대퇴사두"),
    INNER_THIGH("내전근"),
    CALVES("종아리"),
    NECK("목"),
    CARDIO("유산소"),
    OTHER("기타");

    private final String koreanName;

    Part(String koreanName) {
        this.koreanName = koreanName;
    }

    public String getKoreanName() {
        return koreanName;
    }
}
