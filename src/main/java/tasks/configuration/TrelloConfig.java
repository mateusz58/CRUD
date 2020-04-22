package tasks.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
@Getter
@PropertySource("classpath:trello.properties")
public class TrelloConfig {

	@Value("${trello.api.endpoint.prod}")
	private String trelloApiEndpoint;

	@Value("${trello.app.key}")
	private String trelloAppKey;

	@Value("${trello.app.token}")
	private String trelloToken;

	@Value("${trello.app.username}")
	private String trelloUsername;
}

