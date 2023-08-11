package kr.co.test.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import kr.co.test.security.APIUserDetailsService;
import kr.co.test.security.filter.APILoginFilter;
import kr.co.test.security.filter.RefreshTokenFilter;
import kr.co.test.security.filter.TokenCheckFilter;
import kr.co.test.security.handler.APILoginSuccessHandler;
import kr.co.test.utill.JWTUtil;
import lombok.extern.java.Log;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Log
public class CustomSecurityConfig {
	
	// 서비스객체 주입
	@Autowired
	private APIUserDetailsService apiUserDetailsService;
	// JWTUtil 주입
	@Autowired
	private JWTUtil jwtUtil;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		log.info("-----------web configure-----------");
		return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
	}
	
	@Bean
	public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
		// AuthenticationManager 설정
		AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.userDetailsService(apiUserDetailsService).passwordEncoder(passwordEncoder());
		
		// AuthenticationManager 객체 생성
		AuthenticationManager authenticationManager = authenticationManagerBuilder.build();
		
		
		http.authenticationManager(authenticationManager);
		
		// 필터
		APILoginFilter apiLoginFilter = new APILoginFilter("/generateToken");
		apiLoginFilter.setAuthenticationManager(authenticationManager);
		// 핸들러
		APILoginSuccessHandler successHandler = new APILoginSuccessHandler(jwtUtil);
		apiLoginFilter.setAuthenticationSuccessHandler(successHandler);
		
		// 필터동작위치
		http.addFilterBefore(apiLoginFilter, UsernamePasswordAuthenticationFilter.class);
		
		// 토큰체크필터
		TokenCheckFilter tokenCheckFilter = new TokenCheckFilter(jwtUtil);
		http.addFilterBefore(tokenCheckFilter, UsernamePasswordAuthenticationFilter.class);
		
		// refreshToken 필터
		RefreshTokenFilter refreshTokenFilter = new RefreshTokenFilter(jwtUtil, "/refreshToken");
		http.addFilterBefore(refreshTokenFilter, TokenCheckFilter.class);
		
		log.info("-------configure-------------");
		http.csrf().disable(); // CSRF 토큰 비활성화
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 세션 미사용
		
		// cors설정
		http.cors(configurer->{
			configurer.configurationSource(corsConfigurationSource());
		});
		return http.build();
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOriginPatterns(Arrays.asList("http://localhost","http://localhost:8000","http://localhost:3000"));
		config.setAllowedMethods(Arrays.asList("GET","POST"));
		config.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control","Content-Type"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}
}
