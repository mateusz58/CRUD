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
	private  String trelloApiEndpoint;

	@Value("${trello.app.key}")
	private  String trelloAppKey;

	@Value("${trello.app.token}")
	private String trelloToken;

	@Value("${trello.app.username}")
	private String trelloUsername;

	public URI urlGetBoards() {
		return UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/members/" +
				trelloUsername + "/boards")
				.queryParam("key", trelloAppKey)
				.queryParam("token", trelloToken)
				.queryParam("fields", "name,id")
				.queryParam("lists", "all").build().encode().toUri();
	}

	public URI urlCreateCard() {
		return UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/members/" +
				trelloUsername + "/boards")
				.queryParam("key", trelloAppKey)
				.queryParam("token", trelloToken)
				.queryParam("fields", "name,id")
				.queryParam("lists", "all").build().encode().toUri();
	}
}
