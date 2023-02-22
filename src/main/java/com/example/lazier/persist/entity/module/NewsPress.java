package com.example.lazier.persist.entity.module;


import com.example.lazier.dto.module.NewsPressDto;
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
@Entity(name = "NEWS_PRESS")
@Builder
public class NewsPress {

  @Id
  private String pressId; //oid

  private String pressName;
  private String sector;

  private LocalDateTime updatedAt;

  public NewsPress(NewsPressDto dto){
    this.pressId = dto.getPressId();
    this.pressName = dto.getPressName();
    this.sector = dto.getSector();
    this.updatedAt = LocalDateTime.now();
  }


}



