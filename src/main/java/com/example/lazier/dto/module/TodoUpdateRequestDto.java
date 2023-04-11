package com.example.lazier.dto.module;

import com.example.lazier.persist.entity.module.Todo;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodoUpdateRequestDto {

	@Size(min = 20)
	private String content;
	
	private String id;

	public static List<TodoUpdateRequestDto> of(List<Todo> list) {
		List<TodoUpdateRequestDto> todoUpdateRequestDtoList = new ArrayList<>();
		for (Todo todo : list) {
			todoUpdateRequestDtoList.add(TodoUpdateRequestDto.of(todo));
		}
		return todoUpdateRequestDtoList;
	}

	public static TodoUpdateRequestDto of(Todo todo) {
		return TodoUpdateRequestDto.builder()
			.id(todo.getId().toString())
			.content(todo.getContent())
			.build();
	}
}
