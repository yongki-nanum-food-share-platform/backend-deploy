package yongkinanum.backenddeploy.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;
import yongkinanum.backenddeploy.MyRestDoc;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@Sql(value = "classpath:import.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class ChatIntegrationTest extends MyRestDoc {
    @Autowired
    ObjectMapper om;

    @Test
    @DisplayName("채팅방 참가 테스트")
    @WithUserDetails(value = "alstjr12")
    void enter_chat_test() throws Exception {
        //given
        ChatRequest.EnterDTO enterDTO = new ChatRequest.EnterDTO();
        enterDTO.setIdx(1L);

        String requestBody = om.writeValueAsString(enterDTO);

        //when
        ResultActions resultActions = mvc.perform(
                post("/chats/enter")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value("true"))
                .andDo(print())
                .andDo(document);
    }

    @Test
    @DisplayName("채팅방 목록 조회 테스트")
    @WithUserDetails(value = "alstjr12")
    void find_all_chat_test() throws Exception {
        //given

        //when
        ResultActions resultActions = mvc.perform(
                get("/chats")
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value("true"))
                .andDo(print())
                .andDo(document);
    }

    @Test
    @DisplayName("채팅방 조회 테스트")
    @WithUserDetails(value = "alstjr12")
    void find__chat_test() throws Exception {
        //given
        Long idx = 1L;

        //when
        ResultActions resultActions = mvc.perform(
                get("/chats/{id}", idx)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value("true"))
                .andDo(print())
                .andDo(document);
    }

    @Test
    @DisplayName("채팅방 나가기 테스트")
    @WithUserDetails(value = "alstjr12")
    void delete_chat_test() throws Exception {
        //given
        Long idx = 1L;

        //when
        ResultActions resultActions = mvc.perform(
                delete("/chats/{id}/delete", idx)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value("true"))
                .andDo(print())
                .andDo(document);
    }
}