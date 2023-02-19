package com.example.lazier.persist.entity.user;

import com.example.lazier.dto.user.MemberInfo;
import com.example.lazier.persist.entity.module.UserWeather;
import com.example.lazier.persist.entity.module.Weather;
import com.example.lazier.persist.entity.todo.Todo;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Builder
public class LazierUser implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	@Column(nullable = false, length = 100, unique = true)
	private String userEmail; //oauth

	private String userName; //oauth

	private String oauthId; //oauth

	private String password;

	private String phoneNumber;

	private LocalDateTime createdAt;

	private String userStatus;

	private String socialType;

	private String dataStatus; //수정

	private String emailAuthKey;
	private boolean emailAuthYn;
	private LocalDateTime emailAuthDt;

	@OneToMany(mappedBy = "lazierUser", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST,
		orphanRemoval = true)
	private List<Todo> todo = new ArrayList<>();

	@OneToMany(mappedBy = "lazierUser", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST,
		orphanRemoval = true)
	private List<Weather> weather = new ArrayList<>();

	@OneToMany(mappedBy = "lazierUser", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST,
		orphanRemoval = true)
	private List<UserWeather> userWeather = new ArrayList<>();
	public void delete() {
		for (Todo todo : this.getTodo()) {
			todo.setLazierUser(null);
		}
	}

	public void updateUserInfo(MemberInfo memberInfo) {
		if (!memberInfo.getSocialType().toLowerCase().trim().equals("google")) {
			this.userEmail = memberInfo.getUserEmail();
		}
		this.userName = memberInfo.getUserName();
		this.phoneNumber = memberInfo.getPhoneNumber();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getUsername() {
		return String.valueOf(userId);
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
