package tasks.controller.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import tasks.domain.Badges;
import tasks.domain.dto.*;
import tasks.domain.entity.TrelloBoard;
import tasks.domain.TrelloList;
import tasks.service.ServiceFacade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BoardsController.class)
class BoardsControllerTest {

    @MockBean
    ServiceFacade trelloFacade;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    TrelloBoardDto trelloBoardDto;
    TrelloBoard trelloBoard;

    TrelloList trelloList;

    Badges badges;
    CreatedTrelloCartDto responseTrelloCartDto;

    String originEndPoint = "/api/v1/trello/";

    @BeforeEach
    void createSampleData() {
        trelloList = new TrelloList("1", "test_list", false);

        trelloBoardDto = new TrelloBoardDto("1", "testBoard", Collections.singletonList(trelloList));
        trelloBoard = new TrelloBoard("1", "testEntity", Collections.singletonList(trelloList));

        responseTrelloCartDto = new CreatedTrelloCartDto("1", "card", "com/org");
    }

    @Test
    @DisplayName("GET api/v1/trello/boards - fetch empty board")
    public void getBoardsMethodShouldReturnEmptyListOfTrelloBoards() throws Exception {
        //Given
        String ending = "boards";
        String endPoint = String.format("%s/%s",originEndPoint,ending);
        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        when(trelloFacade.fetchAndValidateTrelloBoards()).thenReturn(trelloBoards);

        //When & Then
        mockMvc.perform(get(endPoint)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @DisplayName("GET api/v1/trello/boards - fetch all boards")
    public void  getBoardsMethodShouldReturnAllBoards() throws Exception {
        //Given
        List<TrelloBoardDto> trelloBoardsDto = Collections.singletonList(trelloBoardDto);

        when(trelloFacade.fetchAndValidateTrelloBoards()).thenReturn(trelloBoardsDto);

        //When & Then
        mockMvc.perform(get("/api/v1/trello/boards")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(trelloBoardsDto)));
    }
}
