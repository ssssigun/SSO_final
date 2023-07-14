package kr.co.test;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.co.test.utill.JWTUtil;
import lombok.extern.java.Log;

@SpringBootTest
@Log
public class JWTUtilTests {

	@Autowired
	private JWTUtil jwtUtil;
	
	@Test
	public void testGenerate() {
		Map<String, Object> claimMap = Map.of("mid", "ABCDE");
		String jwtStr = jwtUtil.generationToken(claimMap, 1);
		log.info(jwtStr);
	}
	
	@Test
	public void testValidate() {
		
		String jwtStr = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2ODg0MDAxMzcsIm1pZCI6IkFCQ0RFIiwiaWF0IjoxNjg4NDAwMDc3fQ.UarbBUMlj2PMziZzlXv2QLESeYRaYSyHlvUjXfuGdhM";
		Map<String, Object> claim = jwtUtil.validateToken(jwtStr);
		System.out.println(claim);
	}
	
	@Test
	public void testAll() {
		String jwtStr = jwtUtil.generationToken(Map.of("mid","hong", "email", "hong@gmail.com"), 1);
		
		log.info(jwtStr);
		
		Map<String, Object> claim = jwtUtil.validateToken(jwtStr);
		log.info("MID:"+claim.get("mid"));
		log.info("EMAIL:"+claim.get("email"));
	}
}
