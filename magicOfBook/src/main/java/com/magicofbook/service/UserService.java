package com.magicofbook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.magicofbook.dto.UserDto;
import com.magicofbook.entity.User;
import com.magicofbook.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepo;
	
	public User logInValidation(UserDto userDto){
		if(userRepo.existsById(userDto.getEmail())){
			User user= userRepo.findById(userDto.getEmail()).get();
			System.out.println(user.toString());
			if(userDto.getPassword().equals(user.getPassword())){
				return user;
			}
		}
		return null;
	}  
	
	
	public User getUser(String email){
		if(userRepo.existsById(email)){
			return userRepo.findById(email).get();
		}
		return null;
	}
	public List<User> getAllUsers(){
		return userRepo.findAll();
	}


	public User createUser(User user) {
		// TODO Auto-generated method stub
		if(!userRepo.existsById(user.getEmail())){
			return userRepo.save(user);
		}
		return null;
	}


	public User updateUser(User user) {
		// TODO Auto-generated method stub
		return userRepo.save(user);
		
	}
	

}
