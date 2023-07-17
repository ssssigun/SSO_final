package kr.co.test.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import kr.co.test.domain.APIUser;
import kr.co.test.repository.APIUserRepository;

@RestController
@RequestMapping("/api")
public class SampleController {
	
	@Autowired
	APIUserRepository uRepo;
	
	@GetMapping("/doA")
	public List<String> doA() {
		return Arrays.asList("AAA","BBB","CCC");
	}
//	처음 서비스 등록할 때 유저정보 가져오기
//	이름, 이메일, 아이디
	@ResponseBody
	@PostMapping("/userRegister.do")
	public APIUser check(@RequestParam("id")String id) {
		List<APIUser> user = uRepo.findAPIUserById(id);
		return user.get(0);
	}
}
