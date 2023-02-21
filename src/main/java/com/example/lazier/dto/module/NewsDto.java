package com.example.lazier.dto.module;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewsDto {

  private String newsId;

  private String Subject;
  private String PressId;
  private LocalDateTime createdAt;

  private String url;
  private String imagePath;

  private LocalDateTime updatedAt;

  public NewsDto(String newsId) {
    this.newsId = newsId;
  }

}
