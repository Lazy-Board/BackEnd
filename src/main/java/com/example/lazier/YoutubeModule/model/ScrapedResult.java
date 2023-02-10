package com.example.lazier.YoutubeModule.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
public class ScrapedResult {
    private List<Youtube> youtubeList;

    public ScrapedResult() {
        this.youtubeList = new ArrayList<>();
    }

}
