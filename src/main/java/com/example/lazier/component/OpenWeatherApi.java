package com.example.lazier.component;

import com.example.lazier.dto.module.WeatherDto;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OpenWeatherApi {

    @Value("${open.weather.url}")
    private String statisticUrl;
    @Value("${open.weather.key}")
    private String apiKey;

    public WeatherDto getWeather(String lat, String lon) {
        String data = getData(lat, lon);

        JSONParser jsonParser = new JSONParser();

        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(data);

            JSONArray weather = (JSONArray) jsonObject.get("weather");
            JSONObject object = (JSONObject) weather.get(0);
            String icon = object.get("icon").toString(); // 아이콘
            String description = object.get("description").toString(); // 상세정보 (구름조금)
            String main = object.get("main").toString(); // 날씨 (clouds)
            String id = object.get("id").toString();

            JSONObject mainObject = (JSONObject) jsonObject.get("main");
            String temperature = mainObject.get("temp").toString();
            String feelsLike = mainObject.get("feels_like").toString(); // 체감온도
            String highestTemperature = mainObject.get("temp_max").toString(); // 최고기온
            String lowestTemperature = mainObject.get("temp_min").toString(); // 최저기온
            String pressure = mainObject.get("pressure").toString(); // 기압(단위 hPa)
            String humidity = mainObject.get("humidity").toString(); // 습도 (%)

            JSONObject windObject = (JSONObject) jsonObject.get("wind");
            String windSpeed = windObject.get("speed").toString(); // 풍속 (단위 : m/s)
            String windDirection = windObject.get("deg").toString(); // 풍향

            return WeatherDto.builder()
                .icon(icon)
                .description(description)
                .engWeather(main)
                .weatherId(id)
                .temperature(temperature)
                .effectiveTemperature(feelsLike)
                .highestTemperature(highestTemperature)
                .lowestTemperature(lowestTemperature)
                .pressure(pressure)
                .humidity(humidity)
                .windSpeed(windSpeed)
                .windDirection(windDirection)
                .updatedAt(updatedDateText(LocalDateTime.now()))
                .build();

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public String getData(String lat, String lon) {
        try {
            String apiUrl = String.format(statisticUrl, lat, lon, apiKey);

            URL url = new URL(apiUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            BufferedReader br;

            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }

            String inputLine;

            StringBuilder response = new StringBuilder();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();

            return response.toString();
        } catch (Exception e) {
            return "failed to get response";
        }
    }

    public String updatedDateText(LocalDateTime now) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        return now != null ? now.format(formatter) : "";
    }
}
