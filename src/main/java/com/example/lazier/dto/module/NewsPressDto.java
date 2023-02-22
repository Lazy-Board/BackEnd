package com.example.lazier.dto.module;

import com.example.lazier.persist.entity.module.NewsPress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewsPressDto {
  private String pressId; //oid

  private String pressName;
  private String sector;
  public static NewsPressDto from (NewsPress entity){
    return NewsPressDto.builder()
        .pressId(entity.getPressId())
        .pressName(entity.getPressName())
        .sector(entity.getSector())
        .build();
  }
  public NewsPressDto (String sector){
    this.sector = sector;
  }


  }
