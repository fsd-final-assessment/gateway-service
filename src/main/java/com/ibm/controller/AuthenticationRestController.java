package com.ibm.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.dto.TokenResponse;
import com.ibm.dto.UserDto;
import com.ibm.security.JwtTokenUtil;
import com.ibm.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class AuthenticationRestController {
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private UserService userService;

	@PostMapping(value = "/authenticate")
	public ResponseEntity<TokenResponse> createAuthenticationToken(@RequestBody UserDto userDto)
			throws AuthenticationException {

		authenticate(userDto.getUsername(), userDto.getPassword());

		final UserDetails userDetails = userDetailsService.loadUserByUsername(userDto.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new TokenResponse(userDetails.getUsername(), token));
	}
	
	@PostMapping("/register")
	public ResponseEntity<Boolean> register(@RequestBody UserDto userDto) throws Exception {
		log.info("register:"+userDto);
		Boolean result = userService.register(userDto);
		return ResponseEntity.ok(result);
	}
	
	private void authenticate(String username, String password) throws AuthenticationException{
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}
