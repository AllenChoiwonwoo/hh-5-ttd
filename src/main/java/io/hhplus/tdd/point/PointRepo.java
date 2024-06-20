package io.hhplus.tdd.point;

public interface PointRepo {
    UserPoint addPoint(UserPoint userPoint);

    UserPoint getPoint(long userId);

    UserPoint setPoint(long userId, long amount);
}
