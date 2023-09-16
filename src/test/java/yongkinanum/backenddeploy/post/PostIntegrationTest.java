package yongkinanum.backenddeploy.post;

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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@Sql(value = "classpath:import.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class PostIntegrationTest extends MyRestDoc {
    @Autowired
    ObjectMapper om;

    @Test
    @DisplayName("게시물 작성 테스트")
    @WithUserDetails("alstjr12")
    void write_post_test() throws Exception {
        //given
        PostRequest.WriteDTO writeDTO = new PostRequest.WriteDTO();
        writeDTO.setTitle("테스트 제목");
        writeDTO.setContent("테스트 내용");
        writeDTO.setTime("17시");
        writeDTO.setPeople("2");
        writeDTO.setPlace("광안동");
        writeDTO.setIdx(1L);

        List<PostRequest.WriteDTO.OptionDTO> optionDTOs = new ArrayList<>();
        PostRequest.WriteDTO.OptionDTO optionDTO1 = new PostRequest.WriteDTO.OptionDTO();
        optionDTO1.setIdx(1L);
        optionDTO1.setQuantity(5);
        optionDTOs.add(optionDTO1);

        PostRequest.WriteDTO.OptionDTO optionDTO2 = new PostRequest.WriteDTO.OptionDTO();
        optionDTO2.setIdx(2L);
        optionDTO2.setQuantity(5);
        optionDTOs.add(optionDTO2);

        writeDTO.setOptions(optionDTOs);

        String requestBody = om.writeValueAsString(writeDTO);

        //when
        ResultActions resultActions = mvc.perform(
                post("/posts/write")
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
    @DisplayName("전체 게시물 조회 테스트")
    @WithUserDetails("alstjr12")
    void find_all_post_test() throws Exception {
        //given

        //when
        ResultActions resultActions = mvc.perform(
                get("/posts")
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value("true"))
                .andDo(print())
                .andDo(document);

    }

    @Test
    @DisplayName("게시물 조회 테스트")
    @WithUserDetails("alstjr12")
    void find_post_test() throws Exception {
        //given
        Long idx = 1L;

        //when
        ResultActions resultActions = mvc.perform(
                get("/posts/{id}", idx)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value("true"))
                .andDo(print())
                .andDo(document);
    }

    @Test
    @DisplayName("게시물 검색 테스트")
    @WithUserDetails("alstjr12")
    void search_post_test() throws Exception {
        //given
        PostRequest.FindSpecificDTO findSpecificDTO = new PostRequest.FindSpecificDTO();
        findSpecificDTO.setTitle("치킨");

        String requestBody = om.writeValueAsString(findSpecificDTO);

        //when
        ResultActions resultActions = mvc.perform(
                post("/posts/search")
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
    @DisplayName("게시물 수정 테스트")
    @WithUserDetails("alstjr12")
    void update_post_test() throws Exception {
        //given
        Long idx = 1L;

        PostRequest.UpdateDTO updateDTO = new PostRequest.UpdateDTO();
        updateDTO.setTitle("수정 테스트");
        updateDTO.setContent("수정 내용");
        updateDTO.setTime("18시");
        updateDTO.setPlace("연산동");
        updateDTO.setPeople("3");

        String requestBody = om.writeValueAsString(updateDTO);

        //when
        ResultActions resultActions = mvc.perform(
                put("/posts/{id}/update", idx)
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
    @DisplayName("게시물 삭제 테스트")
    @WithUserDetails("alstjr12")
    void delete_post_test() throws Exception {
        //given
        Long idx = 1L;

        //when
        ResultActions resultActions = mvc.perform(
                delete("/posts/{id}/delete", idx)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value("true"))
                .andDo(print())
                .andDo(document);

    }
}