package com.example.lazier.YoutubeModule.service;

import com.example.lazier.YoutubeModule.persist.repository.YoutubeRepository;
import com.example.lazier.YoutubeModule.scrapper.YoutubeScrapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class YoutubeService {
    private final YoutubeRepository youtubeRepository;
    private final YoutubeScrapper youtubeScrapper;

}
