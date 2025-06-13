package madebyzino.HealthyGram.common.exception;

public enum UploadDirectory {
    PROFILE_IMAGE("profile_image");

    private String value;

    UploadDirectory(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
