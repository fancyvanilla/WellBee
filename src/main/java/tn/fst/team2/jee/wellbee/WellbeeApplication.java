package tn.fst.team2.jee.wellbee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@SpringBootApplication
@ComponentScan(basePackages = "tn.fst.team2.jee")
@EnableJpaRepositories(basePackages = "tn.fst.team2.jee")
@EntityScan(basePackages = "tn.fst.team2.jee")
public class WellbeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(WellbeeApplication.class, args);
	}

}
