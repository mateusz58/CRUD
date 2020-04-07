import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;
import tasks.client.ClientRestTemplateHelper;
import tasks.configuration.TrelloConfig;
import tasks.domain.dto.TrelloBoardDto;

@ExtendWith(SpringExtension.class)
public class TrelloClientTest {

	@InjectMocks
	private ClientRestTemplateHelper trelloClient;

	@Mock
	private RestTemplate restTemplate;

	@Mock
	private TrelloConfig trelloConfig;

	@BeforeEach
	public void init() {
		when(trelloConfig.getTrelloApiEndpoint()).thenReturn("http://test.com");
		when(trelloConfig.getTrelloAppKey()).thenReturn("test");
		when(trelloConfig.getTrelloToken()).thenReturn("test");
	}

	@Test
	public void shouldReturnEmptyList() throws URISyntaxException {
		//Given
		URI uri = new URI("http://test.com/members/" + "testuser"
				+ "/boards?key=test&token=test&fields=name,id&lists=all");

		when(restTemplate.getForObject(uri, TrelloBoardDto[].class)).thenReturn(null);

		//When
		List<TrelloBoardDto> trelloBoards = trelloClient.getForList(TrelloBoardDto.class, trelloConfig.urlGetBoards().toString());

		//Then
		assertEquals(0, trelloBoards.size());
	}
}