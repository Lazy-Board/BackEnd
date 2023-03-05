package com.example.lazier.service.module;

import com.example.lazier.dto.module.TodoDeleteRequestDto;
import com.example.lazier.dto.module.TodoUpdateRequestDto;
import com.example.lazier.dto.module.TodoWriteRequestDto;
import com.example.lazier.dto.module.TodoWriteResponseDto;
import com.example.lazier.exception.todo.AlreadyDeleteException;
import com.example.lazier.exception.todo.FailedWriteException;
import com.example.lazier.persist.entity.module.Todo;
import com.example.lazier.persist.entity.user.LazierUser;
import com.example.lazier.persist.repository.TodoRepository;
import com.example.lazier.service.user.MyPageService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TodoService {

	private final MyPageService myPageService;
	private final TodoRepository todoRepository;

	public TodoWriteResponseDto write(HttpServletRequest request,
		TodoWriteRequestDto todoWriteRequestDto) {
		Long userId = myPageService.parseUserId(request);
		LazierUser lazierUser = myPageService.searchMember(userId);

		if (todoRepository.countByUserId(userId) + 1 > 3) {
			throw new FailedWriteException("할 일은 3개까지 작성할 수 있습니다.");
		}
		Todo todo = todoRepository.save(Todo.of(lazierUser, todoWriteRequestDto));

		return TodoWriteResponseDto.builder()
			.id(todo.getId().toString())
			.content(todo.getContent())
			.build();
	}

	@Transactional
	public void delete(TodoDeleteRequestDto todoDeleteRequestDto) {
		if (todoDeleteRequestDto.getId() == null || todoDeleteRequestDto.getId().equals("")) {
			throw new AlreadyDeleteException("이미 삭제된 글입니다.");
		}

		Todo todo = todoRepository.findById(Long.valueOf(todoDeleteRequestDto.getId()))
			.orElseThrow(() -> new AlreadyDeleteException("이미 삭제된 글입니다."));
		todoRepository.delete(todo);
	}

	@Transactional
	public void update(TodoUpdateRequestDto todoUpdateRequestDto) {
		if (todoUpdateRequestDto.getId() == null || todoUpdateRequestDto.getId().equals("")) {
			throw new AlreadyDeleteException("이미 삭제된 글입니다.");
		}

		Todo todo = todoRepository.findById(Long.valueOf(todoUpdateRequestDto.getId()))
			.orElseThrow(() -> new AlreadyDeleteException("이미 삭제된 글입니다."));
		todo.setContent(todoUpdateRequestDto.getContent());
	}

	public List<TodoUpdateRequestDto> search(HttpServletRequest request) {
		List<Todo> list = todoRepository.findAllByUserId(myPageService.parseUserId(request));

		if (list == null) {
			return null;
		}
		return TodoUpdateRequestDto.of(list);
	}
}
