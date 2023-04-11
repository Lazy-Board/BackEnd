package com.example.lazier.dto.module;

import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodoWriteRequestDto {
	
	@Size(min = 20)
	private String content;
}
