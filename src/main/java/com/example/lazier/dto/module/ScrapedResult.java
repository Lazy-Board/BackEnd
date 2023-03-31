package com.example.lazier.dto.module;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
public class ScrapedResult {

  private List<YoutubeDto> youtubeDtoList;

  public ScrapedResult() {
    this.youtubeDtoList = new ArrayList<>();
  }

}
