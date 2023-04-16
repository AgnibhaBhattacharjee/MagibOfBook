package com.magicofbook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.magicofbook.dto.UserDto;
import com.magicofbook.entity.Admin;
import com.magicofbook.repository.AdminRepository;

@Service
public class AdminService {
	
	@Autowired
	AdminRepository adminRepo;
	
	public Admin logInValidation(UserDto userDto){
		if(adminRepo.existsById(userDto.getEmail())){
			Admin admin= adminRepo.findById(userDto.getEmail()).get();
			if(userDto.getPassword().equals(admin.getPassword())){
				return admin;
			}
		}
		return null;
	}  
	
	
	public Admin getAdmin(String email){
		if(adminRepo.existsById(email)){
			return adminRepo.findById(email).get();
		}
		return null;
	}
	public List<Admin> getAllAdmins(){
		return adminRepo.findAll();
	}


	public Admin createAdmin(Admin admin) {
		// TODO Auto-generated method stub
		
		if(!adminRepo.existsById(admin.getEmail())){
			return adminRepo.save(admin);
		}
		return null;
	}


	public Admin updateAdmin(Admin admin) {
		// TODO Auto-generated method stub
		return adminRepo.save(admin);
	}

}
