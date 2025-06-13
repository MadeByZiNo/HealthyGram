package madebyzino.HealthyGram.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import madebyzino.HealthyGram.domain.user.usermeta.Gender;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserMetaInitializeRequest {
    private String nickname;
    private String name;
    private String bio;
    private Gender gender;
    private LocalDate birthDate;
}
