package com.example.lazier.persist.entity.module;

import com.example.lazier.dto.module.TodoWriteRequestDto;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private LazierUser lazierUser;

	//private Long userId;
	private String content;

	public static Todo of(LazierUser lazierUser, TodoWriteRequestDto todoWriteRequestDto) {
		return Todo.builder()
			.lazierUser(lazierUser)
			.content(todoWriteRequestDto.getContent())
			.build();
	}

}
