package yongkinanum.backenddeploy.order;

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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@Sql(value = "classpath:import.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class OrderIntegrationTest extends MyRestDoc {
    @Autowired
    ObjectMapper om;

    @Test
    @DisplayName("결제 테스트")
    @WithUserDetails(value = "alstjr12")
    void save_order_test() throws Exception {
        //given

        //when
        ResultActions resultActions = mvc.perform(
                post("/orders/save")
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value("true"))
                .andDo(print())
                .andDo(document);
    }

    @Test
    @DisplayName("결제정보 조회 테스트")
    @WithUserDetails(value = "alstjr12")
    void find_order_info_test() throws Exception {
        //given
        OrderRequest.InfoDTO infoDTO = new OrderRequest.InfoDTO();
        infoDTO.setPostIdx(1L);

        String requestBody = om.writeValueAsString(infoDTO);

        //when
        ResultActions resultActions = mvc.perform(
                post("/orders/info")
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
    @DisplayName("전체 주문 조회 테스트")
    @WithUserDetails(value = "alstjr12")
    void find_all_order_test() throws Exception {
        //given

        //when
        ResultActions resultActions = mvc.perform(
                get("/orders")
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value("true"))
                .andDo(print())
                .andDo(document);
    }

    @Test
    @DisplayName("주문 조회 테스트")
    @WithUserDetails(value = "alstjr12")
    void find_order_test() throws Exception {
        //given
        Long id = 1L;

        //when
        ResultActions resultActions = mvc.perform(
                get("/orders/{id}", id)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value("true"))
                .andDo(print())
                .andDo(document);
    }

    @Test
    @DisplayName("취소 주문 조회 테스트")
    @WithUserDetails(value = "alstjr12")
    void find_cancel_order_test() throws Exception {
        //given

        //when
        ResultActions resultActions = mvc.perform(
                get("/orders/cancel")
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value("true"))
                .andDo(print())
                .andDo(document);
    }

    @Test
    @DisplayName("주문 취소 테스트")
    @WithUserDetails(value = "alstjr12")
    void cancel_order_test() throws Exception {
        //given
        Long id = 1L;

        //when
        ResultActions resultActions = mvc.perform(
                delete("/orders/{id}/delete", id)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value("true"))
                .andDo(print())
                .andDo(document);
    }
}