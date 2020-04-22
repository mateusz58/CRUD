package tasks.controller.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tasks.domain.Badges;
import tasks.domain.entity.TrelloBoard;
import tasks.domain.TrelloCard;
import tasks.domain.TrelloList;
import tasks.domain.dto.*;
import tasks.service.ServiceFacade;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(CardsController.class)
class CardsControllerTest {


    @MockBean
    ServiceFacade service;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;


    TrelloBoardDto trelloBoardDto;
    TrelloBoard trelloBoard;

    TrelloCardDto trelloCardDto;
    TrelloCard trelloCard;

    TrelloList trelloList;

    Badges badges;
    CreatedTrelloCartDto responseTrelloCartDto;

    String originEndPoint = "/api/v1/trello/";

    @BeforeEach
    void createSampleData() {
        trelloList = new TrelloList("1", "test_list", false);
        trelloBoardDto = new TrelloBoardDto("1", "test", Collections.singletonList(trelloList));
        trelloBoard = new TrelloBoard("1", "test", Collections.singletonList(trelloList));
        trelloCard = new TrelloCard("card", "description", "pos", "1");
        trelloCardDto = new TrelloCardDto("card", "description", "pos", "1");
        badges = new Badges(5, new
                AttachmentsByType(new Trello(3, 4)));
        responseTrelloCartDto = new CreatedTrelloCartDto("1", "card", "com/org");
    }

    @Test
    @DisplayName("POST api/v1/trello/cards - create card")
    public void createTrelloCard() throws Exception {
        //Given
        String ending = "cards";
        String endPoint = String.format("%s/%s", originEndPoint, ending);
        when(service.performCartCreationWithEmailNotification(ArgumentMatchers.any(TrelloCardDto.class))).thenReturn(responseTrelloCartDto);
        String requestContent = mapper.writeValueAsString(trelloCardDto);
        String responseContent = mapper.writeValueAsString(responseTrelloCartDto);

        //When & then
        mockMvc.perform(MockMvcRequestBuilders.post(endPoint)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestContent))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(responseContent))
                .andExpect(status().isCreated());
    }
}