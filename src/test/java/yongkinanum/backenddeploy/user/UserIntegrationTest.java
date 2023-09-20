package yongkinanum.backenddeploy.user;

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

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@Sql(value = "classpath:import.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class UserIntegrationTest extends MyRestDoc {
    @Autowired
    private ObjectMapper om;

    @Test
    @DisplayName("회원가입 테스트")
    void join_test() throws Exception {
        //given
        UserRequest.RegistDTO registDTO = new UserRequest.RegistDTO();
        registDTO.setUserId("gominseok12");
        registDTO.setPassword("@@alstjr12");
        registDTO.setUserName("고민석");
        registDTO.setRole("일반회원");

        String requestBody = om.writeValueAsString(registDTO);

        //when
        ResultActions resultActions = mvc.perform(
                post("/users/regist")
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
    @DisplayName("로그인 테스트")
    void login_test() throws Exception {
        //given
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setUserId("rhalstjr1999");
        loginDTO.setPassword("@@alstjr12");

        String requestBody = om.writeValueAsString(loginDTO);

        //when
        ResultActions resultActions = mvc.perform(
                post("/users/login")
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
    @DisplayName("회원 정보 조회")
    @WithUserDetails(value = "alstjr12")
    void find_user_info_test() throws Exception {
        //given

        //when
        ResultActions resultActions = mvc.perform(
                get("/users")
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value("true"))
                .andDo(print())
                .andDo(document);
    }

    @Test
    @DisplayName("특정 회원 정보 조회")
    void find_specific_user_info_test() throws Exception {
        //given
        Long idx = 3L;

        //when
        ResultActions resultActions = mvc.perform(
                get("/users/{id}", idx)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value("true"))
                .andDo(print())
                .andDo(document);
    }

    @Test
    @DisplayName("회원이 보유한 가게 조회")
    @WithUserDetails(value = "alstjr1999")
    void find_user_shop_info_test() throws Exception {
        //given
        Long idx = 2L;

        //when
        ResultActions resultActions = mvc.perform(
                get("/users/{id}/shop", idx)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value("true"))
                .andDo(print())
                .andDo(document);
    }

    @Test
    @DisplayName("유저 주소 추가 테스트")
    @WithUserDetails(value = "alstjr12")
    void add_user_address_test() throws Exception {
        //given
        UserRequest.AddAddressDTO addAddressDTO = new UserRequest.AddAddressDTO();
        addAddressDTO.setAddress("안락동");

        String requestBody = om.writeValueAsString(addAddressDTO);

        ResultActions resultActions = mvc.perform(
                post("/users/address/add")
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
    @DisplayName("유저 주소 조회 테스트")
    @WithUserDetails(value = "alstjr12")
    void find_user_address_test() throws Exception {
        //given

        //when
        ResultActions resultActions = mvc.perform(
                get("/users/address")
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value("true"))
                .andDo(print())
                .andDo(document);
    }

    @Test
    @DisplayName("유저 정보 수정 테스트 - 닉네임, 비밀번호")
    @WithUserDetails(value = "alstjr12")
    void update_user_info_test() throws Exception {
        //given
        UserRequest.UpdateDTO updateDTO = new UserRequest.UpdateDTO();
        updateDTO.setNewNickname("앙앙기모띠");
        updateDTO.setOldPassword("@@alstjr12");
        updateDTO.setNewPassword("@@alstjr12");

        String requestBody = om.writeValueAsString(updateDTO);

        ResultActions resultActions = mvc.perform(
                put("/users/update")
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
    @DisplayName("유저 정보 수정 테스트 - 닉네임")
    @WithUserDetails(value = "alstjr12")
    void update_user_name_test() throws Exception {
        //given
        UserRequest.UpdateDTO updateDTO = new UserRequest.UpdateDTO();
        updateDTO.setNewNickname("앙앙기모띠");

        String requestBody = om.writeValueAsString(updateDTO);

        ResultActions resultActions = mvc.perform(
                put("/users/update")
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
    @DisplayName("유저 정보 수정 테스트 - 비밀번호")
    @WithUserDetails(value = "alstjr12")
    void update_user_password_test() throws Exception {
        //given
        UserRequest.UpdateDTO updateDTO = new UserRequest.UpdateDTO();
        updateDTO.setOldPassword("@@alstjr12");
        updateDTO.setNewPassword("@@alstjr12");

        String requestBody = om.writeValueAsString(updateDTO);

        ResultActions resultActions = mvc.perform(
                put("/users/update")
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
    @DisplayName("유저 탈퇴 테스트")
    @WithUserDetails(value = "alstjr12")
    void delete_user_test() throws Exception {
        //given

        //when
        ResultActions resultActions = mvc.perform(
                delete("/users/unregist")
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value("true"))
                .andDo(print())
                .andDo(document);
    }
}