package com.eljhoset.todo.persistence;

import com.eljhoset.todo.model.Todo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends PagingAndSortingRepository<Todo, Long>, JpaSpecificationExecutor<Todo> {


}