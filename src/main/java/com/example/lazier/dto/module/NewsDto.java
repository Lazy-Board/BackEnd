package com.example.lazier.dto.module;

import com.example.lazier.persist.entity.module.News;
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

  private String subject;
  private String contents;
  private String pressId;
  private String pressName;
  private String createdAt;

  private String url;
  private String imagePath;

  private LocalDateTime updatedAt;


  public static NewsDto from(News entity) {
    return NewsDto.builder()
        .newsId(entity.getNewsId())
        .subject(entity.getSubject())
        .contents(entity.getContents())
        .pressId(entity.getPressId())
        .pressName(entity.getPressName())
        .createdAt(entity.getCreatedAt())
        .url(entity.getUrl())
        .imagePath(entity.getImagePath())
        .build();
  }

}
