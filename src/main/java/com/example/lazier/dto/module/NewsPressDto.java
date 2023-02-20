package com.example.lazier.dto.module;

import com.example.lazier.persist.entity.module.NewsPress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsPressDto {
  private String pressId; //oid

  private String pressName;
  private String sector;
  @Builder
  public NewsPressDto from(NewsPress entity){

    this.pressId = entity.getPressId();
    this.pressName = entity.getPressName();
    this.sector = entity.getSector();
    return this;
  }
  public NewsPressDto (String sector){
    this.sector = sector;
  }


  }
