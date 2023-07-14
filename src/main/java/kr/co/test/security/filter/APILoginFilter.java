package kr.co.test.security.filter;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.java.Log;

@Log
public class APILoginFilter extends AbstractAuthenticationProcessingFilter {
	
	public APILoginFilter(String defaultFilterProcessesUrl) {
		super(defaultFilterProcessesUrl);
		// TODO Auto-generated constructor stub
	}
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
		log.info("APILoginFilter");
		Map<String, String> json = parseRequestJSON(request);
		System.out.println(json);
		
		// 인증처리
		UsernamePasswordAuthenticationToken authenticationToken 
			= new UsernamePasswordAuthenticationToken(json.get("mid"), json.get("mpw"));
		
		return getAuthenticationManager().authenticate(authenticationToken);
	}
	
	private Map<String, String> parseRequestJSON(HttpServletRequest request) {
		try (Reader reader = new InputStreamReader(request.getInputStream())) {
			ObjectMapper om = new ObjectMapper();
			return om.readValue(reader, Map.class);
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		return null;
	}
}
