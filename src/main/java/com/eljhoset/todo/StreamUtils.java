package com.eljhoset.todo;

import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public interface StreamUtils {
    static <T> UnaryOperator<T> peek(Consumer<T> consumer) {
        return t -> {
            consumer.accept(t);
            return t;
        };
    }
}
