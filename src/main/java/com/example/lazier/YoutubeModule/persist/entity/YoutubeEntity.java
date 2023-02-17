package com.example.lazier.YoutubeModule.persist.entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "YOUTUBE")
@Builder
public class YoutubeEntity {

  @Id
  private String videoId;

  private String channelName;
  private String contentName;
  private String hit;
  private LocalDateTime createdAt;
  private String length;

  private String imagePath;

  private LocalDateTime updatedAt;


}



