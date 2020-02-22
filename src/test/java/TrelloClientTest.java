import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;
import tasks.client.TrelloClient;
import tasks.client.TrelloConfig;
import tasks.domain.TrelloBoardDto;

@RunWith(MockitoJUnitRunner.class)
public class TrelloClientTest {

	@InjectMocks
	private TrelloClient trelloClient;

	@Mock
	private RestTemplate restTemplate;

	@Mock
	private TrelloConfig trelloConfig;

	@Before
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
		List<TrelloBoardDto> trelloBoards = trelloClient.getTrelloBoards();

		//Then
		assertEquals(0, trelloBoards.size());
	}
}