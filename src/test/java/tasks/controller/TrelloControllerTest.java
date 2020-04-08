package tasks.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import tasks.domain.TrelloBoard;
import tasks.domain.TrelloCard;
import tasks.domain.TrelloList;
import tasks.domain.dto.*;
import tasks.service.ServiceFacade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TrelloController.class)
class TrelloControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceFacade trelloFacade;

    TrelloBoardDto trelloBoardDto;
    TrelloBoard trelloBoard;

    TrelloCardDto trelloCardDto;
    TrelloCard trelloCard;

    TrelloList trelloList;
    TrelloListDto trelloListDto;

    Badges badges;
    ResponseTrelloCartDto responseTrelloCartDto;

    String originEndPoint = "api/v1/trello/";

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
    public void shouldFetchEmptyTrelloBoards() throws Exception {
        //Given
        String endPoint = originEndPoint + "boards/kodilla";
        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        when(trelloFacade.fetchAndValidateTrelloBoards()).thenReturn(trelloBoards);

        //When & Then
        mockMvc.perform(get(endPoint).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldFetchTrelloBoards() throws Exception {
        //Given
        List<TrelloListDto> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloListDto("1", "Test List", false));

        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoardDto("1", "Test Task", trelloLists));

        when(trelloFacade.fetchAndValidateTrelloBoards()).thenReturn(trelloBoards);

        //When & Then
        mockMvc.perform(get("api/v1/trello/boards").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                //Trello board fields
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is("1")))
                .andExpect(jsonPath("$[0].name", is("Test Task")))
                //Trello list fields
                .andExpect(jsonPath("$[0].lists", hasSize(1)))
                .andExpect(jsonPath("$[0].lists[0].id", is("1")))
                .andExpect(jsonPath("$[0].lists[0].name", is("Test List")))
                .andExpect(jsonPath("$[0].lists[0].closed", is(false)));
    }

//    @Test
//    public void shouldCreateTrelloCard() throws Exception {
//        //Given
//        TrelloCardDto trelloCardDto = new TrelloCardDto(
//                "Test",
//                "Test description",
//                "top",
//                "1"
//        );
//
//        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto(
//                "323",
//                "Test",
//                "http://test.com",
//                new TrelloBadgesDto(
//                        5,
//                        new TrelloAttachmentsByTypeDto(
//                                new TrelloTrelloDto(
//                                        8,
//                                        6
//                                )
//                        )
//                )
//        );
//
//        when(trelloFacade.createCard(ArgumentMatchers.any(TrelloCardDto.class))).thenReturn(createdTrelloCardDto);
//
//        Gson gson = new Gson();
//        String jsonContent=gson.toJson(trelloCardDto);
//
//        //When & then
//        mockMvc.perform(post("/v1/trello/cards")
//                .contentType(MediaType.APPLICATION_JSON)
//                .characterEncoding("UTF-8")
//                .content(jsonContent))
//                .andExpect(jsonPath("$.id",is("323")))
//                .andExpect(jsonPath("$.name",is("Test")))
//                .andExpect(jsonPath("$.shortUrl",is("http://test.com")));
//    }
}