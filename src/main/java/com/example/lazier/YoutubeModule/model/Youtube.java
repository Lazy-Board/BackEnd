package com.example.lazier.YoutubeModule.model;

import com.example.lazier.YoutubeModule.persist.entity.YoutubeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Youtube {

  private String videoId;
  private String channelName;
  private String contentName;
  private String hit; // 조회수
  private LocalDateTime createdAt;
  private String length;
  private String imagePath;
  private LocalDateTime updatedAt;
  @Builder
  public Youtube from(YoutubeEntity entity){

    this.videoId = entity.getVideoId();
    this.channelName = entity.getChannelName();
    this.contentName = entity.getContentName();
    this.hit = entity.getHit();
    this.createdAt = entity.getCreatedAt();
    this.length = entity.getLength();
    this.imagePath = entity.getImagePath();

    return this;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }
}
