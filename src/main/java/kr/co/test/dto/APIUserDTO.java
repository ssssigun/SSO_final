package kr.co.test.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class APIUserDTO extends User {
	private String id;
	private String pwd;
	
	public APIUserDTO(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.id = username;
		this.pwd = password;
	}
}
