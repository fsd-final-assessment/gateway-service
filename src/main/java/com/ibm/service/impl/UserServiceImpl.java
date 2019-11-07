package com.ibm.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ibm.dto.UserDto;
import com.ibm.entity.User;
import com.ibm.repository.UserRepository;
import com.ibm.service.UserService;

import javassist.NotFoundException;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	private final ModelMapper modelMapper;

	public UserServiceImpl(UserRepository userRepository,BCryptPasswordEncoder bCryptPasswordEncoder,
			ModelMapper modelMapper) {
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.modelMapper = modelMapper;
	}
	
	@Override
	public Boolean register(UserDto userDto) throws Exception {

		List<User> userList = userRepository.getByUsername(userDto.getUsername());

		if (userList.size() > 0) {
			throw new Exception("User exist with this : " + userDto.getUsername());
		}

		User user = new User();
		user = modelMapper.map(userDto, User.class);
		user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
		user.setCreateDate(LocalDateTime.now());
		userRepository.save(user);
		return true;

	}
	
	@Override
	public UserDto findByUsername(String username) throws NotFoundException {
		try {

			User user = userRepository.findByUsername(username);
			UserDto userDto = modelMapper.map(user, UserDto.class);
			return userDto;

		} catch (Exception e) {

			throw new NotFoundException("User dosen't exist with this name called : " + username);
		}
	}

}
