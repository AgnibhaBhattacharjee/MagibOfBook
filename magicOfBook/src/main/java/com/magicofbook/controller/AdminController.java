package com.magicofbook.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.magicofbook.dto.ResponseDto;
import com.magicofbook.dto.UserDto;
import com.magicofbook.entity.Admin;
import com.magicofbook.entity.Book;
import com.magicofbook.entity.User;
import com.magicofbook.service.AdminService;
import com.magicofbook.service.BookService;

@RestController
@RequestMapping("/admins")
public class AdminController {
	@Autowired
	AdminService adminService;
	@Autowired
	BookService bookService;
	
	@PostMapping("/login")
	public ResponseEntity<ResponseDto> login(@RequestBody UserDto userDto ,HttpSession session){
		String email= (String) session.getAttribute("email");
		if(email==null){
			System.out.println(userDto);
			Admin loggedInAdmin= adminService.logInValidation(userDto);
			if(loggedInAdmin!=null){
				session.setAttribute("email", userDto.getEmail());
				session.setAttribute("adminname", loggedInAdmin.getUserName());
				System.out.println(session.getAttribute("adminname")+" logged In");
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("User "+loggedInAdmin.getUserName()+" logged In",userDto));
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("Invalid Credentials",null));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("Admin "+
				session.getAttribute("email")+" already logged in",null));
	}
	
	@GetMapping
	public List<Admin> getAllAdmins(){
//		session.setAttribute("email", userDto.getEmail());
//		session.setAttribute("username", userService.getUser(userDto.getEmail()).getUserName());
		return adminService.getAllAdmins();
	}
	
	@GetMapping("/logout")
	public ResponseEntity<ResponseDto> logout(HttpSession session){
		String adminName=(String) session.getAttribute("adminname");
		String email= (String) session.getAttribute("email");
		if(adminName!=null && email!=null){
			session.removeAttribute("email");
			session.removeAttribute("adminname");
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("Admin "+adminName+" logged out",null));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("No Admin Logged In",null));
	}
	
	@PostMapping("/register")
	public ResponseEntity<ResponseDto> register(@RequestBody Admin admin){
		//session.setAttribute("email", user.getEmail());
		if(admin.getEmail().endsWith("@admin.com")){
			Admin newAdmin= adminService.createAdmin(admin);
			if(newAdmin!=null){
				//session.setAttribute("username", loggedInUser.getUserName());
				//System.out.println(session.getAttribute("username")+" logged In");
				return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto("New Admin "+newAdmin.getUserName()+" Created",newAdmin));
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("Admin Already exists with this email id: "+admin.getEmail(),null));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("Domain Missmatch- Admin email must have domain of @admin.com ",null));
	}
	
	@PutMapping("/lovedBooks/{bookId}")
	public ResponseEntity<ResponseDto> addToLovedBooks(@PathVariable int bookId, HttpSession session){
		String email=(String) session.getAttribute("email");
		System.out.println("Inside addToLovedBooks email is "+email);
		if(email!=null){
			Admin admin= adminService.getAdmin(email);
			if(admin!=null){
				admin.getLovedBooks().add(bookService.getBooksByid(bookId));
				Admin updatedAdmin= adminService.updateAdmin(admin);
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseDto(bookService.getBooksByid(bookId).getBookTitle()+
								" is added to loved books of admin "+admin.getUserName(),updatedAdmin));
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(new ResponseDto(bookService.getBooksByid(bookId).getBookTitle()+
					" can not be added to loved books of admin ",null));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("Admin is not logged in",null));
		
	}
	@PutMapping("/readLaterBooks/{bookId}")
	public ResponseEntity<ResponseDto> addToReadLaterBooks(@PathVariable int bookId, HttpSession session){
		String email=(String) session.getAttribute("email");
		if(email!=null){
			Admin admin= adminService.getAdmin(email);
			if(admin!=null){
				admin.getReadLaterBooks().add(bookService.getBooksByid(bookId));
				Admin updatedAdmin= adminService.updateAdmin(admin);
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseDto(bookService.getBooksByid(bookId).getBookTitle()+
								" is added to read later books of admin "+admin.getUserName(),updatedAdmin));
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(new ResponseDto(bookService.getBooksByid(bookId).getBookTitle()+
					" can not be added to read Later books of admin ",null));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("Admin is not logged in",null));
		
	}
	
	@GetMapping("/lovedBooks")
	public ResponseEntity<ResponseDto> viewLovedBooks(HttpSession session){
		String email=(String) session.getAttribute("email");
		if(email!=null){
			Admin admin= adminService.getAdmin(email);
			if(admin!=null){
				List<Book> lovedBooks=  admin.getLovedBooks();
				//User updateduser= userService.updateUser(user);
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseDto("The Loved Books of admin: "+admin.getUserName(),lovedBooks));
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(new ResponseDto("No Admin with the email id: "+email,null));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("Admin is not logged in",null));
		
	}
	@GetMapping("/readLaterBooks")
	public ResponseEntity<ResponseDto> viewReadLaterBooks(HttpSession session){
		String email=(String) session.getAttribute("email");
		if(email!=null){
			Admin admin= adminService.getAdmin(email);
			if(admin!=null){
				List<Book> readLaterBooks=  admin.getReadLaterBooks();
				//User updateduser= userService.updateUser(user);
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseDto("The Read later Books of admin: "+admin.getUserName(),readLaterBooks));
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(new ResponseDto("No Admin with the email id: "+email,null));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("Admin is not logged in",null));
		
	}


}