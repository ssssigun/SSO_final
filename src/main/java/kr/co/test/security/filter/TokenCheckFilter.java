package kr.co.test.security.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import kr.co.test.security.exception.AccessTokenException;
import kr.co.test.utill.JWTUtil;
import lombok.extern.java.Log;

@Log
public class TokenCheckFilter extends OncePerRequestFilter {
	private JWTUtil jwtUtil;
	
	public TokenCheckFilter (JWTUtil jwUtil) {
		this.jwtUtil = jwUtil;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String path = request.getRequestURI();
		if (!path.startsWith("/api/")) { // api 주소가 아니면(일반접속이면) 통과
			filterChain.doFilter(request, response);
			return;
		}
		log.info("Token Check Filter............");
		log.info("JWUtil: "+jwtUtil.toString());
		
		// AccessToken 검증
		try {
			validateAccessToken(request);
			filterChain.doFilter(request, response);			
		} catch (AccessTokenException e) {
			e.sendResponseError(response);
		}
		
		
	}
	
	private Map<String, Object> validateAccessToken(HttpServletRequest request) throws AccessTokenException {
		String headerStr = request.getHeader("Authorization");
		if (headerStr == null || headerStr.length() < 8) {
			throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.UNACCEPT);
		}
		// Bearer 생략
		String tokenType = headerStr.substring(0,6);
		String tokenStr = headerStr.substring(7);
		
		if (tokenType.equalsIgnoreCase("Bearer") == false) {
			throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.BADTYPE);
		}
		
		try {
			Map<String, Object> values = jwtUtil.validateToken(tokenStr);
			return values;
		} catch (MalformedJwtException e) {
			log.info("MalformedJwtException................");
			throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.MALFORM);
		} catch (SignatureException e) {
			log.info("SignaturedException..................");
			throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.BADSIGN);
		} catch(ExpiredJwtException e) {
			log.info("ExpiredJwtException..................");
			throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.EXPIRED);
		}
	}

}
