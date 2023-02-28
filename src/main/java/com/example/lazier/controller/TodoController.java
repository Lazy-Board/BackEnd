package com.example.lazier.controller;

import com.example.lazier.dto.module.TodoDeleteRequestDto;
import com.example.lazier.dto.module.TodoUpdateRequestDto;
import com.example.lazier.dto.module.TodoWriteRequestDto;
import com.example.lazier.service.module.TodoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/todolist")
@RequiredArgsConstructor
public class TodoController {

	private final TodoService todoService;

	@ApiOperation(value = "투두리스트 작성", notes = "투두리스트 작성하기")
	@ApiResponses({
		@ApiResponse(code = 200, message = "작성 완료"),
		@ApiResponse(code = 400, message = "리스트를 이미 3개까지 작성한 경우")
	})
	@PostMapping("/write")
	public ResponseEntity<?> write(HttpServletRequest request,
		@RequestBody TodoWriteRequestDto todoWriteRequestDto) {

		return new ResponseEntity<>(todoService.write(request, todoWriteRequestDto), HttpStatus.OK);
	}


	@ApiOperation(value = "투두리스트 삭제", notes = "투두리스트 삭제하기")
	@ApiResponses({
		@ApiResponse(code = 200, message = "삭제 완료"),
		@ApiResponse(code = 400, message = "이미 삭제된 경우")
	})
	@PostMapping("/delete")
	public ResponseEntity<?> delete(@RequestBody TodoDeleteRequestDto todoDeleteRequestDto) {

		todoService.delete(todoDeleteRequestDto);
		return ResponseEntity.ok("투두리스트 삭제 완료");
	}


	@ApiOperation(value = "투두리스트 수정", notes = "투두리스트 수정하기")
	@ApiResponses({
		@ApiResponse(code = 200, message = "수정 완료"),
		@ApiResponse(code = 400, message = "이미 삭제된 경우")
	})
	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestBody TodoUpdateRequestDto todoUpdateRequestDto) {

		todoService.update(todoUpdateRequestDto);
		return ResponseEntity.ok("투두리스트 수정 완료");
	}


	@ApiOperation(value = "투두리스트 조회", notes = "투두리스트 조회하기")
	@ApiResponse(code = 200, message = "조회 완료")
	@GetMapping("/search")
	public ResponseEntity<?> search(HttpServletRequest request) {
		return new ResponseEntity<>(todoService.search(request), HttpStatus.OK);
	}

}
