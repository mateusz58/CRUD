package tasks.client;

import static java.util.Optional.ofNullable;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import tasks.domain.CreatedTrelloCardDto;
import tasks.domain.TrelloBoardDto;
import tasks.domain.TrelloCardDto;

@Component
public class TrelloClient {

	private static final Logger log = LoggerFactory.getLogger((TrelloClient.class));

	private RestTemplate restTemplate;

	private TrelloConfig trelloConfig;

	public TrelloClient(RestTemplate restTemplate, TrelloConfig trelloConfig) {
		this.restTemplate = restTemplate;
		this.trelloConfig = trelloConfig;
	}


	private URI urlBuilder() {
		return UriComponentsBuilder.fromHttpUrl(trelloConfig.getTrelloApiEndpoint() + "/members/" +
				trelloConfig.getTrelloUsername() + "/boards")
				.queryParam("key", trelloConfig.getTrelloAppKey())
				.queryParam("token", trelloConfig.getTrelloToken())
				.queryParam("fields", "name,id")
				.queryParam("lists", "all").build().encode().toUri();
	}

	public List<TrelloBoardDto> getTrelloBoards() {
		try {
			TrelloBoardDto[] boardsResponse = restTemplate.getForObject(urlBuilder(), TrelloBoardDto[].class);
			return Arrays.asList(ofNullable(boardsResponse).orElse(new TrelloBoardDto[0]));
		} catch (RestClientException e) {
			log.error(e.getMessage(), e);
			return new ArrayList<>();
		}
	}

	public CreatedTrelloCardDto createNewCard(TrelloCardDto trelloCardDto) {
		URI url = UriComponentsBuilder.fromHttpUrl(trelloConfig.getTrelloApiEndpoint() + "/cards")
				.queryParam("key", trelloConfig.getTrelloAppKey())
				.queryParam("token", trelloConfig.getTrelloToken())
				.queryParam("name", trelloCardDto.getName())
				.queryParam("desc", trelloCardDto.getDescription())
				.queryParam("pos", trelloCardDto.getPos())
				.queryParam("idList", trelloCardDto.getListId()).build().encode().toUri();
		return restTemplate.postForObject(url, null, CreatedTrelloCardDto.class);
	}
}
