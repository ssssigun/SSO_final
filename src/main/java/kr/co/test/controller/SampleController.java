package kr.co.test.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import kr.co.test.domain.APIUser;
import kr.co.test.repository.APIUserRepository;
import kr.co.test.utill.JWTUtil;

@RestController
@RequestMapping("/api")
public class SampleController {
	
	@Autowired
	APIUserRepository uRepo;
	@Autowired
	JWTUtil jwt;
	
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
	
// 토큰을 보내면 아이디를 파싱해서 pk 반환하기
	@GetMapping("/confirm.do")
	@ResponseBody
	public int confirm(HttpServletRequest request) {
		//토큰 가져오기
		String headerStr = request.getHeader("Authorization");
		String tokenStr = headerStr.substring(7);
		//토큰 파싱
		Map<String, Object> claim = jwt.validateToken(tokenStr);
		//pk 값 반환
		return uRepo.findById(claim.get("id").toString()).get().getUser_pk();
	}
}
