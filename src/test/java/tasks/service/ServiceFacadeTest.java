package tasks.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tasks.domain.TrelloBoard;
import tasks.domain.TrelloCard;
import tasks.domain.TrelloList;
import tasks.domain.dto.*;
import tasks.domain.mapper.DtoEntityMapper;
import tasks.validator.TrelloValidator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServiceFacadeTest {

    @InjectMocks
    private ServiceFacade trelloFacade;

    @Mock
    private TrelloService trelloService;

    @Mock
    private TrelloValidator trelloValidator;

    @Mock
    private DtoEntityMapper mapper;

    TrelloBoardDto trelloBoardDto;
    TrelloBoard trelloBoard;

    TrelloCardDto trelloCardDto;
    TrelloCard trelloCard;

    TrelloList trelloList;
    TrelloListDto trelloListDto;

    Badges badges;
    ResponseTrelloCartDto responseTrelloCartDto;

    @BeforeEach
    void createSampleData() {
        trelloListDto = new TrelloListDto("1", "test_list", false);
        trelloList = new TrelloList("1", "test_list", false);

        trelloBoardDto = new TrelloBoardDto("1", "test", Collections.singletonList(trelloListDto));
        trelloBoard = new TrelloBoard("1", "test", Collections.singletonList(trelloList));

        trelloCard = new TrelloCard("card", "description", "pos", "1");
        trelloCardDto =  new TrelloCardDto("card", "description", "pos", "1");

        badges = new Badges(5, new
                AttachmentsByType(new TrelloDto(3,4)));

        responseTrelloCartDto = new ResponseTrelloCartDto("1", "card", "com/org", badges);
    }

    @Test
    public void shouldFetchEmptyList() {
        //Given
        when(trelloService.fetchTrelloBoards()).thenReturn(Collections.singletonList(trelloBoardDto));
//        when(mapper.toDto(anyList())).thenReturn(new ArrayList<>());
        when(trelloValidator.validateTrelloBoards(Collections.singletonList(trelloBoard))).thenReturn(new ArrayList<>());

        //When
        List<TrelloBoardDto> trelloBoardDtos = trelloFacade.fetchAndValidateTrelloBoards();

        //Then
        assertNotNull(trelloBoardDtos);
        assertEquals(0, trelloBoardDtos.size());
    }

    @Test
    public void shouldFetchTrelloBoards() {
        //Given
        when(trelloService.fetchTrelloBoards()).thenReturn(Collections.singletonList(trelloBoardDto));
        when(mapper.toEntity(trelloBoardDto)).thenReturn(trelloBoard);
        when(mapper.toDto(anyList())).thenReturn(Collections.singletonList(trelloBoardDto));
        when(trelloValidator.validateTrelloBoards(Collections.singletonList(trelloBoard))).thenReturn(Collections.singletonList(trelloBoard));

        //When
        List<TrelloBoardDto> trelloBoardDtos = trelloFacade.fetchAndValidateTrelloBoards();

        //Then
        assertNotNull(trelloBoardDtos);
        assertEquals(1, trelloBoardDtos.size());

        trelloBoardDtos.forEach(trelloBoardDto -> {
            assertEquals("1", trelloBoardDto.getId());
            assertEquals("my_task", trelloBoardDto.getName());

            trelloBoardDto.getLists().forEach(trelloListDto -> {
                assertEquals("1", trelloListDto.getId());
                assertEquals("my_list", trelloListDto.getName());
                assertEquals(false, trelloListDto.isClosed());
            });
        });
    }

    @Test
    public void shouldCreateCreatedTrelloCardDto() {
        //Given
        Badges trelloBadgesDto = new Badges(5, new
                AttachmentsByType(new TrelloDto(3,4)));

        when(mapper.toEntity(trelloCardDto)).thenReturn(trelloCard);
        when(mapper.toDto(trelloCard)).thenReturn(trelloCardDto);
        when(trelloService.createTrelloCardAndSendEmailNotification(trelloCardDto)).thenReturn(responseTrelloCartDto);

        //When
        ResponseTrelloCartDto createdTrelloCardDto = trelloFacade.postCartCreate(trelloCardDto);

        //Then
        assertEquals("1",createdTrelloCardDto.getId());
        assertEquals("card",createdTrelloCardDto.getName());
        assertEquals("com/org",createdTrelloCardDto.getShortUrl());
        assertEquals(5,createdTrelloCardDto.getBadges().getVotes());
        assertEquals(3,createdTrelloCardDto.getBadges().getAttachmentsByType().getTrelloDto().getBoard());
        assertEquals(4,createdTrelloCardDto.getBadges().getAttachmentsByType().getTrelloDto().getCard());
    }
}