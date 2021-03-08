package jpabook.jpashop.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MemberApiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Test
    @DisplayName("회원등록_api_v1")
    public void 회원등록_api_v1() throws Exception {
        // given
        MemberDto memberDto = new MemberDto("member1");

        final String jsonStr = mapper.writeValueAsString(memberDto);

        // when
        final ResultActions resultActions = mockMvc.perform(post("/api/v1/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonStr))
                .andDo(print());

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"));

    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String name;
    }
}