package com.example.lazier.persist.entity.module;


import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "YOUTUBE")
@Builder
public class News {

  @Id
  private String newsId;

  private String Subject;
  private String PressId;
  private LocalDateTime createdAt;

  private String url;
  private String imagePath;

  private LocalDateTime updatedAt;


}



