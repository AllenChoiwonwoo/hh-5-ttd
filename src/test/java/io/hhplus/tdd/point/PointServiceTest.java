package io.hhplus.tdd.point;

import org.apache.catalina.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PointServiceTest {

    @InjectMocks
    private PointService pointService;

    @Mock
    private PointRepo pointRepo;

    @BeforeEach
    public void setup(){

    }

    @Test
    @DisplayName("포인트 조회 테스트")
    public void success_point_get() {
        // given
        UserPoint userPoint = new UserPoint(10l, 100l, System.currentTimeMillis());
        doReturn(userPoint).when(pointRepo).getPoint(10l);
        // when
        UserPoint result = pointService.get(10l);
        // then
        assertEquals(10l, result.id());
        assertEquals(100l, result.point());
    }

    @Test
    @DisplayName("포인트 조회 테스트 - 없는 유저")
    public void fail_point_get() {
        // given
        UserPoint userPoint = new UserPoint(10l, 100l, System.currentTimeMillis());
        doReturn(null).when(pointRepo).getPoint(10l);
        // when , then
        assertThrows(NoSuchUserException.class, () -> {
            pointService.get(10l);
        });
    }


    @Test
    @DisplayName("포인트 충전 테스트")
    public void success_point_charge(){

        // given
        UserPoint userPointBefore = new UserPoint(10l, 10l, System.currentTimeMillis());
        doReturn(userPointBefore).when(pointRepo).getPoint(10l);
        UserPoint userPointAfter = new UserPoint(10l, 110l, System.currentTimeMillis());
        doReturn(userPointAfter).when(pointRepo).setPoint(10l, 110l);
        // when
        UserPoint result = pointService.charge(10l, 100l);
        // then
        assertEquals(110l, result.point());
    }

    @Test
    @DisplayName("포인트 사용 테스트")
    public void success_point_use() {
        //given
        UserPoint userPointBefore = new UserPoint(10l, 100l, System.currentTimeMillis());
        doReturn(userPointBefore).when(pointRepo).getPoint(10l);
        UserPoint userPointAfter = new UserPoint(10l, 50l, System.currentTimeMillis());
        doReturn(userPointAfter).when(pointRepo).setPoint(10l, 50l);
        //when
        UserPoint result = pointService.use(10l, 50l);
        // then
        assertEquals(50l, result.point());
    }

    @Test
    @DisplayName("포인트 사용 테스트 - 포인트 부족")
    public void fail_point_use() {
        // given
        UserPoint userPointBefore = new UserPoint(10l, 100l, System.currentTimeMillis());
        doReturn(userPointBefore).when(pointRepo).getPoint(10l);
        //when
        //then
        assertThrows(RuntimeException.class, () -> {
            pointService.use(10l, 500l);
        });
    }
}
