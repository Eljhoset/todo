package com.eljhoset.todo.persistence;

import com.eljhoset.todo.model.FilterTodo;
import com.eljhoset.todo.model.Todo;
import com.eljhoset.todo.model.Todo_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.Optional;
import java.util.stream.Stream;

public interface TodoSpecifications {
    static Specification<Todo> filterTodos(FilterTodo filter) {
        return (root, query, criteriaBuilder) -> {
            Optional<Predicate> nameOrNote = filter.getName().map(name ->
                    Stream.of(root.get(Todo_.name), root.get(Todo_.note))
                            .map(field -> containsIgnoringCase(criteriaBuilder, field, name))
                            .reduce(criteriaBuilder.or(), criteriaBuilder::or)
            );
            Optional<Predicate> isDone = filter.isDone().map(done -> criteriaBuilder.equal(root.get(Todo_.done), done));

            return Stream.of(nameOrNote, isDone).flatMap(Optional::stream).reduce(criteriaBuilder.and(), criteriaBuilder::and);
        };
    }
    private static Predicate containsIgnoringCase(CriteriaBuilder criteriaBuilder , Path<String>field , String value){
        return criteriaBuilder.like(criteriaBuilder.upper(field), "%" + value.toUpperCase() + "%");
    }
}
