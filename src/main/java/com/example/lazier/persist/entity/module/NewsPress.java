package com.example.lazier.persist.entity.module;


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
@Entity(name = "YOUTUBE")
@Builder
public class NewsPress {

  @Id
  private String pressId; //oid

  private String pressName;
  private String sector;

  private LocalDateTime updatedAt;


}



