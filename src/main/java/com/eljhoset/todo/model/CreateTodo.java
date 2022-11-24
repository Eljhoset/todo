package com.eljhoset.todo.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateTodo {
    @NotBlank
    private String name;
    private String note;

    public Todo todo(){
        Todo todo = new Todo();
        todo.setName(name);
        todo.setNote(note);
        return todo;
    }
}
