package com.example.lazier.YoutubeModule.persist.entity;


import io.jsonwebtoken.lang.Assert;
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
    private String numViewers;
    private String createdAt;
    private String length;

    private String imagePath;

    private LocalDateTime updatedAt;
    public YoutubeEntity(String youtubeId){
        Assert.notNull(youtubeId, "Id must not be null");
        this.videoId = youtubeId;
    }

}



