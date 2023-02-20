package com.example.lazier.persist.entity.module;


import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
public class NewsUser {

  @Id
  private String userId;

  @OneToMany(mappedBy = "pressName")
  private List<NewsPress> userPress;


}



