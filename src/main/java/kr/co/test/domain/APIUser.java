package kr.co.test.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class APIUser {
	private int user_pk;
	@Id
	private String id;
	private String pwd;
	private String creditcardnumber;
	private String name;
	private String email;
	
	public void changePw(String pwd) {
		this.pwd = pwd;
	}
}
