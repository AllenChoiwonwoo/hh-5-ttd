package io.hhplus.tdd.point;

public record UserPoint(
        long id,
        long point,
        long updateMillis
) {

    public static UserPoint empty(long id) {
        return new UserPoint(id, 0, System.currentTimeMillis());
    }

    public UserPoint addPoints(long amount){
        return new UserPoint(this.id(), this.point()+amount, this.updateMillis());
    }

    public UserPoint subtractPoint(long amount) {
        if (amount > this.point) throw new RuntimeException("not enough point");
        return new UserPoint(this.id(), this.point() - amount, this.updateMillis);
    }
}
