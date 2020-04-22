package tasks.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tasks.domain.Badges;
import tasks.domain.entity.TrelloBoard;
import tasks.domain.TrelloCard;
import tasks.domain.TrelloList;
import tasks.domain.dto.*;
import tasks.domain.mapper.CartMapper;
import tasks.exception.BadRequestException;
import tasks.validator.TrelloValidator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServiceFacadeTest {

    @InjectMocks
    private ServiceFacade trelloFacade;

    @Mock
    private TrelloServiceClient trelloServiceClient;

    @Mock
    private TrelloValidator trelloValidator;

    @Mock
    private CartMapper cartMapper;


    TrelloBoard trelloBoard;
    TrelloBoardDto trelloBoardDto;

    TrelloCardDto trelloCardDto;
    TrelloCard trelloCard;

    TrelloList trelloList;

    Badges badges;
    CreatedTrelloCartDto responseTrelloCartDto;

    @BeforeEach
    void createSampleData() {
        trelloList = new TrelloList("1", "trelloList", false);

        trelloBoardDto = new TrelloBoardDto("1", "boardDto", Collections.singletonList(trelloList));
        trelloBoard = new TrelloBoard("1", "boardEntity", Collections.singletonList(trelloList));

        trelloCard = new TrelloCard("cardEntity", "descriptionEntity", "pos", "1");
        trelloCardDto =  new TrelloCardDto("cardDto", "descriptionDto", "pos", "1");

        badges = new Badges(5, new
                AttachmentsByType(new Trello(3,4)));

        responseTrelloCartDto = new CreatedTrelloCartDto("1", "card", "com/org");
    }

    @Test
    public void shouldFetchEmptyList() {
        //Given
        when(trelloServiceClient.fetchAll()).thenReturn(Collections.singletonList(trelloBoardDto));
//        when(mapper.toDto(anyList())).thenReturn(new ArrayList<>());
        when(trelloValidator.validateTrelloBoards(Collections.singletonList(trelloBoardDto))).thenReturn(new ArrayList<>());

        //When
        List<TrelloBoardDto> trelloBoardDtos = trelloFacade.fetchAndValidateTrelloBoards();

        //Then
        assertNotNull(trelloBoardDtos);
        assertEquals(0, trelloBoardDtos.size());
    }

    @Test
    public void shouldFetchTrelloBoards() {
        //Given
        when(trelloServiceClient.fetchAll()).thenReturn(Collections.singletonList(trelloBoardDto));
//        when(mapper.toEntity(trelloBoardDto)).thenReturn(trelloBoard);
//        when(mapper.toDto(anyList())).thenReturn(Collections.singletonList(trelloBoardDto));
        when(trelloValidator.validateTrelloBoards(Collections.singletonList(trelloBoardDto))).thenReturn(Collections.singletonList(trelloBoardDto));

        //When
        List<TrelloBoardDto> trelloBoardDtos = trelloFacade.fetchAndValidateTrelloBoards();

        //Then
        assertNotNull(trelloBoardDtos);
        assertEquals(1, trelloBoardDtos.size());

        trelloBoardDtos.forEach(trelloBoardDto -> {
            assertEquals("1", trelloBoardDto.getId());
            assertEquals("boardDto", trelloBoardDto.getName());

            trelloBoardDto.getLists().forEach(trelloListDto -> {
                assertEquals("1", trelloListDto.getId());
                assertEquals("trelloList", trelloListDto.getName());
                assertEquals(false, trelloListDto.isClosed());
            });
        });
    }

    @Test
    public void shouldCreateCreatedTrelloCardDto() throws BadRequestException {
        //Given

        when(cartMapper.toEntity(trelloCardDto)).thenReturn(trelloCard);
        when(cartMapper.toDto(trelloCard)).thenReturn(trelloCardDto);
        when(trelloServiceClient.createCard(trelloCardDto)).thenReturn(responseTrelloCartDto);

        //When
        CreatedTrelloCartDto actual = trelloFacade.performCartCreationWithEmailNotification(trelloCardDto);

        //Then
        assertEquals(responseTrelloCartDto.getId(),actual.getId());
        assertEquals(responseTrelloCartDto.getName(),actual.getName());
        assertEquals(responseTrelloCartDto.getShortUrl(),actual.getShortUrl());
    }
}