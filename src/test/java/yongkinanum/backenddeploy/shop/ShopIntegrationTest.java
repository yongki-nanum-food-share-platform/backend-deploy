package yongkinanum.backenddeploy.shop;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;
import yongkinanum.backenddeploy.MyRestDoc;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@Sql(value = "classpath:import.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class ShopIntegrationTest extends MyRestDoc {
    @Autowired
    ObjectMapper om;

    @Test
    @DisplayName("가게 등록 테스트")
    @WithUserDetails("alstjr1999")
    void regist_shop_test() throws Exception {
        //given
        ShopRequest.RegistDTO registDTO = new ShopRequest.RegistDTO();
        registDTO.setShopName("굽네치킨 부산토곡점");
        registDTO.setShopAddress("부산광역시 연제구 토곡동");
        registDTO.setBrandName("굽네치킨");
        registDTO.setTip("3000");

        String requestBody = om.writeValueAsString(registDTO);

        //when
        ResultActions resultActions = mvc.perform(
                post("/shops/regist")
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
    @DisplayName("가게 조회 테스트")
    void find_shop_test() throws Exception {
        //given
        Long idx = 1L;

        //when
        ResultActions resultActions = mvc.perform(
                get("/shops/{id}", idx)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value("true"))
                .andDo(print())
                .andDo(document);
    }

    @Test
    @DisplayName("브랜드 조회 테스트")
    void find_brand_test() throws Exception {
        //given
        ShopRequest.FindBrandDTO findBrandDTO = new ShopRequest.FindBrandDTO();
        findBrandDTO.setBrandName("굽네");

        String requestBody = om.writeValueAsString(findBrandDTO);

        //when
        ResultActions resultActions = mvc.perform(
                post("/shops/brand")
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
    @DisplayName("가게 리스트 조회 테스트")
    @WithUserDetails("alstjr12")
    void find_all_shop_test() throws Exception {
        //given
        ShopRequest.FindAllDTO findAllDTO = new ShopRequest.FindAllDTO();
        findAllDTO.setMenuName("굽");

        String requestBody = om.writeValueAsString(findAllDTO);

        //when
        ResultActions resultActions = mvc.perform(
                post("/shops")
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
    @DisplayName("가게 수정 테스트")
    @WithUserDetails(value = "alstjr1999")
    void update_shop_info_test() throws Exception {
        //given
        ShopRequest.UpdateDTO updateDTO = new ShopRequest.UpdateDTO();
        updateDTO.setIdx(1L);
        updateDTO.setNewName("굽네치킨 부산안락안락");
        updateDTO.setNewAddress("부산광역시 동래구 안락동");
        updateDTO.setNewTip("3000");

        String requestBody = om.writeValueAsString(updateDTO);

        ResultActions resultActions = mvc.perform(
                put("/shops/update")
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
    @DisplayName("가게 삭제 테스트")
    @WithUserDetails(value = "alstjr1999")
    void delete_shop_test() throws Exception {
        //given
        ShopRequest.UnregistDTO unregistDTO = new ShopRequest.UnregistDTO();
        unregistDTO.setIdx(1L);

        String requestBody = om.writeValueAsString(unregistDTO);

        //when
        ResultActions resultActions = mvc.perform(
                RestDocumentationRequestBuilders.delete("/shops/unregist")
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
}
