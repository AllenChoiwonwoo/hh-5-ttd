package io.hhplus.tdd.point;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PointControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PointService pointService;

    @Autowired
    ObjectMapper mapper;

    @BeforeEach
    void setUp() {
    }

    @DisplayName("포인트 조회 성공")
    @Test
    void point() throws Exception {
        //given
        UserPoint userPoint = new UserPoint(10l, 100l, System.currentTimeMillis());
        doReturn(userPoint).when(pointService).get(10l);
        //when
        ResultActions resultActions = mvc.perform(get("/point/10"));
        //then
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        UserPoint result = mapper.readValue(contentAsString, UserPoint.class);
        assertEquals(10l, result.id());
        assertEquals(100l, result.point());
    }

    @Test
    void history() {

    }

    @DisplayName("포인트 충전 성공")
    @Test
    void charge() throws Exception {
        // given
        UserPoint userPoint = new UserPoint(10l, 100l, System.currentTimeMillis());
        doReturn(userPoint).when(pointService).charge(10l, 100l);

        // when
        ResultActions resultActions = mvc.perform(patch("/point/10/charge")
                .contentType(MediaType.APPLICATION_JSON)
                .content("100")
        );

        // then
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        UserPoint result = mapper.readValue(contentAsString, UserPoint.class);
        assertEquals(10l, result.id());
        assertEquals(100l, result.point());
    }

    @DisplayName("포인트 사용 성공")
    @Test
    void use() throws Exception {
        // given
        UserPoint userPoint = new UserPoint(10l, 50l, System.currentTimeMillis());
        doReturn(userPoint).when(pointService).use(10l, 50l);

        //when
        ResultActions resultActions = mvc.perform(patch("/point/10/use")
                .contentType(MediaType.APPLICATION_JSON)
                .content("50"));
        //then
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        UserPoint result = mapper.readValue(contentAsString, UserPoint.class);
        assertEquals(10l, result.id());
        assertEquals(50l, result.point());
    }
}