package kr.co.test.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
	@GetMapping("/userRegister.do")
	public Map<String, Optional<APIUser>> check() {
		Map<String, Optional<APIUser>> map = new HashMap<>();
//		map("name", uRepo.findById(null));
		return map;
	}
}
