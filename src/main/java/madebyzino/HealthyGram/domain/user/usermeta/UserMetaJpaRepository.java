package madebyzino.HealthyGram.domain.user.usermeta;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMetaJpaRepository extends JpaRepository<UserMeta, Long> {

    boolean existsByNickname(String nickname);

}
