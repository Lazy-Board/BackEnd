package com.example.lazier.persist.entity.module;


import com.example.lazier.dto.module.NewsDto;
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

  public News(NewsDto dto) {
    this.newsId = dto.getNewsId();
    this.subject = dto.getSubject();
    this.pressId = dto.getPressId();
    this.contents = dto.getContents();
    this.createdAt = dto.getCreatedAt();
    this.url = dto.getUrl();
    this.imagePath = dto.getImagePath();
    this.updatedAt = LocalDateTime.now();
  }


}



