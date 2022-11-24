package com.eljhoset.todo.model;

import lombok.Setter;

import java.util.Optional;

@Setter
public class FilterTodo {
    private String name;
    private Boolean done;

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }
    public Optional<Boolean> isDone() {
        return Optional.ofNullable(done);
    }
}
