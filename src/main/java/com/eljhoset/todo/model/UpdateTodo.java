package com.eljhoset.todo.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class UpdateTodo {
    @NotBlank
    private String name;
    private String note;
    private LocalDateTime dueTime;
    private Boolean done;

    public Todo patch(Todo todo){
        todo.setName(name);
        todo.setNote(note);
        todo.setDone(done);
        todo.setDueTime(dueTime);
        return todo;
    }
}
