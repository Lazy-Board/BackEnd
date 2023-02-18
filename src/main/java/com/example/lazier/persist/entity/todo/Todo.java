package com.example.lazier.persist.entity.todo;

import com.example.lazier.dto.todolist.TodoInfo;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Builder
public class Todo {

	@Id
	private Long id;
	private Long userId;
	private String content;

	public static Todo of(Long userId, TodoInfo todoInfo) {
		return Todo.builder()
			.userId(userId)
			.content(todoInfo.getContent())
			.build();
	}

}
