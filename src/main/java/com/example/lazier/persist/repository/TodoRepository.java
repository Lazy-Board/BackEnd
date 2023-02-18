package com.example.lazier.persist.repository;

import com.example.lazier.persist.entity.todo.Todo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
	Long countByUserId(Long userId);
	boolean existsById(Long id);
	List<Todo> findAllByUserId(Long userId);

}
