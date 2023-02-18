package com.example.lazier.service.todo;

import com.example.lazier.dto.todolist.TodoInfo;
import com.example.lazier.exception.todo.AlreadyDeleteException;
import com.example.lazier.exception.todo.FailedWriteException;
import com.example.lazier.persist.entity.todo.Todo;
import com.example.lazier.persist.repository.TodoRepository;
import com.example.lazier.service.user.MemberService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TodoService {

	private final MemberService memberService;
	private final TodoRepository todoRepository;

	public void write(HttpServletRequest request, TodoInfo todoInfo) {
		Long userId = memberService.parseUserId(request);
		this.memberService.searchMember(userId);

		if (!(todoRepository.countByUserId(userId) <= 3)) {
			throw new FailedWriteException("할 일은 3개까지 작성할 수 있습니다.");
		}
		todoRepository.save(Todo.of(userId, todoInfo));
	}

	public void delete(TodoInfo todoInfo) {
		Todo todo = todoRepository.findById(parseId(todoInfo))
			.orElseThrow(() -> new AlreadyDeleteException("이미 삭제되었습니다"));
		todoRepository.delete(todo);
	}

	public void update(TodoInfo todoInfo) {
		Long id = parseId(todoInfo);

		if (!todoRepository.existsById(id)) {
			throw new AlreadyDeleteException("삭제된 글 입니다.");
		}
		Todo todo = todoRepository.findById(id).get();
		todo.setContent(todoInfo.getContent());
	}

	public List<TodoInfo> search(HttpServletRequest request) {
		List<Todo> list = todoRepository.findAllByUserId(memberService.parseUserId(request));

		if (list == null) return null;
		return TodoInfo.of(list);
	}

	public Long parseId(TodoInfo todoInfo) {
		return Long.valueOf(todoInfo.getId());
	}

}
