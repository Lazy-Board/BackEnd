package com.example.lazier.YoutubeModule.model;

import lombok.AllArgsConstructor;
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
    private String numViewers;
    private String createdAt;
    private String length;

    private String imagePath;

    private LocalDateTime updatedAt;
}
