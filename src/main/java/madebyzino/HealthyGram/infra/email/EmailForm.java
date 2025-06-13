package madebyzino.HealthyGram.infra.email;

public interface EmailForm {
    String getTo();
    String getSubject();
    String getBody();
}