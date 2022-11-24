package com.eljhoset.todo;

import com.eljhoset.todo.model.CreateTodo;
import com.eljhoset.todo.model.FilterTodo;
import com.eljhoset.todo.model.Todo;
import com.eljhoset.todo.model.UpdateTodo;
import com.eljhoset.todo.persistence.TodoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static com.eljhoset.todo.persistence.TodoSpecifications.*;
import static com.eljhoset.todo.StreamUtils.peek;

@RestController
@RequestMapping("/todos")
public class TodoResource {
    private final TodoRepository repository;

    public TodoResource(TodoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    Page<Todo> list(FilterTodo filter, Pageable pageable) {
        return repository.findAll(filterTodos(filter), pageable);
    }

    @GetMapping("/{id}")
    ResponseEntity<Todo> get(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    ResponseEntity<Todo> create(@RequestBody @Valid CreateTodo todo) {
        return Stream.of(todo)
                .map(CreateTodo::todo)
                .map(repository::save)
                .map(saved -> ResponseEntity.status(HttpStatus.CREATED).body(saved))
                .findFirst()
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    ResponseEntity<Todo> update(@PathVariable Long id, @RequestBody @Valid UpdateTodo todo) {
        return repository.findById(id).map(todo::patch)
                .map(repository::save)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable Long id) {
        return repository.findById(id)
                .map(peek(repository::delete))
                .map(ignored -> ResponseEntity.noContent().build())
                .orElse(ResponseEntity.notFound().build());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
