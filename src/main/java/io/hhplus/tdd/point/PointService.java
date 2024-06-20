package io.hhplus.tdd.point;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class PointService {
    private PointRepo pointRepo;
    public UserPoint charge(long userId, long amount) {
        /*
        유저의 포인트 조회
        충전 포인트 더하기
        포인트 저장하기
        저장된 포인트 값 응답
         */
        UserPoint userPoint = pointRepo.getPoint(userId);
        if (isNull(userPoint)) {
            userPoint = UserPoint.empty(userId);
        }
        UserPoint userPointCharged = userPoint.addPoints(amount);
        return pointRepo.setPoint(userId, userPointCharged.point());
    }
    public UserPoint use(long userId, long amount) {
        /*
        포인트 조회
        포인트 포인트 차감
        포인트 저장
         */
        UserPoint userPoint = pointRepo.getPoint(userId);
        UserPoint usedUserPoint = userPoint.subtractPoint(amount);
        return pointRepo.setPoint(userId, usedUserPoint.point());
    }

    public UserPoint get(long userId) {
        UserPoint userPoint = pointRepo.getPoint(userId);
        if (isNull(userPoint)) throw new NoSuchUserException("user id : " + userId);
        return userPoint;
    }
}
