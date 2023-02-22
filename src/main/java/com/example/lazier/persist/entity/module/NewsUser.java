package com.example.lazier.persist.entity.module;


import com.example.lazier.persist.entity.user.LazierUser;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "NEWS_USER")
@Builder
public class NewsUser  {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JoinColumn(name = "user_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private LazierUser lazierUser;


  @OneToMany(mappedBy = "pressName")
  private List<NewsPress> userPress;

  public void update(List<NewsPress> newPressList) {
    this.userPress = newPressList;
  }

}



