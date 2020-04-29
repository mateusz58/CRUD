package tasks.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableAsync
@EnableScheduling
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class,
		HibernateJpaAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
public class CoreConfiguration {

	@Bean
	@Primary
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
