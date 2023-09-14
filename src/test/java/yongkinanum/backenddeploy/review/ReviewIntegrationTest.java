package yongkinanum.backenddeploy.review;

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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@Sql(value = "classpath:import.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class ReviewIntegrationTest extends MyRestDoc {
    @Autowired
    ObjectMapper om;

    @Test
    @DisplayName("리뷰 작성 테스트")
    @WithUserDetails("alstjr12")
    void write_review_test() throws Exception {
        //given
        ReviewRequest.WriteDTO writeDTO = new ReviewRequest.WriteDTO();
        writeDTO.setIdx(1L);
        writeDTO.setContent("테스트 리뷰");
        writeDTO.setStarPoint(0.0F);

        String requestBody = om.writeValueAsString(writeDTO);

        //when
        ResultActions resultActions = mvc.perform(
                post("/reviews/write")
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
    @DisplayName("유저별 리뷰 조회 테스트")
    @WithUserDetails("alstjr12")
    void find_review_test() throws Exception {
        //given

        //when
        ResultActions resultActions = mvc.perform(
                get("/reviews")

        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value("true"))
                .andDo(print())
                .andDo(document);

    }

    @Test
    @DisplayName("리뷰 수정 테스트")
    @WithUserDetails("alstjr12")
    void update_review_test() throws Exception {
        //given
        Long idx = 1L;

        ReviewRequest.UpdateDTO updateDTO = new ReviewRequest.UpdateDTO();
        updateDTO.setContent("리뷰 수정 테스트");

        String requestBody = om.writeValueAsString(updateDTO);

        //when
        ResultActions resultActions = mvc.perform(
                put("/reviews/{id}/update", idx)
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
    @DisplayName("리뷰 삭제 테스트")
    @WithUserDetails("alstjr12")
    void delete_review_test() throws Exception {
        //given
        Long idx = 1L;

        //when
        ResultActions resultActions = mvc.perform(
                delete("/reviews/{id}/delete", idx)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value("true"))
                .andDo(print())
                .andDo(document);

    }
}