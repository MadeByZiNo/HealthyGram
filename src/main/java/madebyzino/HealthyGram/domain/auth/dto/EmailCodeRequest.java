package madebyzino.HealthyGram.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EmailCodeRequest {
    private String email;
    private String code;
}
