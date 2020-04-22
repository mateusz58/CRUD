package tasks.service;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import tasks.exception.BadRequestException;
import tasks.configuration.TrelloConfig;
import tasks.domain.dto.CreatedTrelloCartDto;
import tasks.domain.dto.TrelloBoardDto;
import tasks.domain.dto.TrelloCardDto;
import tasks.helper.ClientRestTemplateHelper;

import static java.util.Optional.ofNullable;

@Service
public class TrelloServiceClient {

    ClientRestTemplateHelper restTemplateHelper;
    SimpleEmailService simpleEmailService;
    TrelloConfig trelloConfig;

    @Autowired
    public TrelloServiceClient(ClientRestTemplateHelper restTemplateHelper, SimpleEmailService simpleEmailService, TrelloConfig trelloConfig) {
        this.restTemplateHelper = restTemplateHelper;
        this.simpleEmailService = simpleEmailService;
        this.trelloConfig = trelloConfig;
    }

    public List<TrelloBoardDto> fetchAll() {
        return restTemplateHelper.getForList(TrelloBoardDto.class, urlGet().toString());
    }

    public CreatedTrelloCartDto createCard(final TrelloCardDto trelloCardDto) throws BadRequestException {
        if (trelloCardDto == null) {
            throw new IllegalArgumentException("Cannot perform action passed object of TrelloCardDto is null");
        }
        return restTemplateHelper.postForObject(urlPost(trelloCardDto),CreatedTrelloCartDto.class);
    }

    private URI urlGet() {
        return UriComponentsBuilder.fromHttpUrl(trelloConfig.getTrelloApiEndpoint() + "/members/" +
                trelloConfig.getTrelloUsername() + "/boards")
                .queryParam("key", trelloConfig.getTrelloAppKey())
                .queryParam("token", trelloConfig.getTrelloToken())
                .queryParam("fields", "name,id")
                .queryParam("lists", "all").build().encode().toUri();
    }

    private URI urlPost(TrelloCardDto object) {
        return UriComponentsBuilder.fromHttpUrl(trelloConfig.getTrelloApiEndpoint() + "/cards")
                .queryParam("key", trelloConfig.getTrelloAppKey())
                .queryParam("token", trelloConfig.getTrelloToken())
                .queryParam("name", object.getName())
                .queryParam("desc", object.getDescription())
                .queryParam("pos", object.getPos())
                .queryParam("idList", object.getListId()).build().encode().toUri();
    }
}
