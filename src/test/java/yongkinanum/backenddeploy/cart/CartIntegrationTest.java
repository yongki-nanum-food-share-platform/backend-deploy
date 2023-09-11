package yongkinanum.backenddeploy.cart;

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

import java.util.ArrayList;
import java.util.List;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@Sql(value = "classpath:import.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class CartIntegrationTest extends MyRestDoc {
    @Autowired
    ObjectMapper om;

    @Test
    @DisplayName("장바구니 등록 테스트")
    @WithUserDetails(value = "alstjr12")
    void add_carts_test() throws Exception {
        //given
        CartRequest.AddDTO addDTO1 = new CartRequest.AddDTO();
        addDTO1.setIdx(1L);
        addDTO1.setQuantity(5);
        addDTO1.setShopName("굽네치킨 부산안락점");

        CartRequest.AddDTO addDTO2 = new CartRequest.AddDTO();
        addDTO2.setIdx(2L);
        addDTO2.setQuantity(1);
        addDTO2.setShopName("굽네치킨 부산안락점");

        List<CartRequest.AddDTO> addDTOs = new ArrayList<>();

        addDTOs.add(addDTO1);
        addDTOs.add(addDTO2);

        String requestBody = om.writeValueAsString(addDTOs);

        //when
        ResultActions resultActions = mvc.perform(
                post("/carts/add")
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
    @DisplayName("장바구니 조회 테스트")
    @WithUserDetails(value = "alstjr12")
    void find_carts_test() throws Exception {
        //given

        //when
        ResultActions resultActions = mvc.perform(
                get("/carts")
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value("true"))
                .andDo(print())
                .andDo(document);
    }

    @Test
    @DisplayName("장바구니 수정 테스트")
    @WithUserDetails(value = "alstjr12")
    void update_carts_test() throws Exception {
        //given
        CartRequest.UpdateDTO updateDTO1 = new CartRequest.UpdateDTO();
        updateDTO1.setIdx(1L);
        updateDTO1.setQuantity(1);

        CartRequest.UpdateDTO updateDTO2 = new CartRequest.UpdateDTO();
        updateDTO2.setIdx(2L);
        updateDTO2.setQuantity(1);

        List<CartRequest.UpdateDTO> updateDTOs = new ArrayList<>();
        updateDTOs.add(updateDTO1);
        updateDTOs.add(updateDTO2);

        String requestBody = om.writeValueAsString(updateDTOs);

        //when
        ResultActions resultActions = mvc.perform(
                put("/carts/update")
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
    @DisplayName("장바구니 삭제 테스트")
    @WithUserDetails(value = "alstjr12")
    void clear_carts_test() throws Exception {
        //given

        //when
        ResultActions resultActions = mvc.perform(
                delete("/carts/clear")
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value("true"))
                .andDo(print())
                .andDo(document);
    }
}
