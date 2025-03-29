package tn.fst.team2.jee.wellbee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import tn.fst.team2.jee.wellbee.mappers.UserMapper;

@SpringBootApplication(scanBasePackages={"tn.fst.team2.jee.wellbee"})
@EnableJpaAuditing
public class WellbeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(WellbeeApplication.class, args);
	}

}
