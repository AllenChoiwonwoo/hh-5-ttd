package io.hhplus.tdd.point;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NoSuchUserException extends RuntimeException {
    public NoSuchUserException(String message) {
        super(message);
    }
}
