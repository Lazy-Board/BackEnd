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
@Entity(name = "NEWS")
@Builder
public class News {

  @Id
  private String newsId;

  private String subject;
  private String pressId;
  private String contents;
  private String createdAt;

  private String url;
  private String imagePath;

  private LocalDateTime updatedAt;


}



