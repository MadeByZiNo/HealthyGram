package madebyzino.HealthyGram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HealthyGramApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealthyGramApplication.class, args);
	}

}
