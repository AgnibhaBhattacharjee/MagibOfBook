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
import com.magicofbook.entity.Book;
import com.magicofbook.entity.User;
import com.magicofbook.service.BookService;
import com.magicofbook.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	UserService userService;
	@Autowired
	BookService bookService;
	
	@PostMapping("/login")
	public ResponseEntity<ResponseDto> login(@RequestBody UserDto userDto ,HttpSession session){
		String email= (String) session.getAttribute("email");
		if(email==null){
			User loggedInUser= userService.logInValidation(userDto);
			if(loggedInUser!=null){
				session.setAttribute("email", userDto.getEmail());
				session.setAttribute("username", loggedInUser.getUserName());
				System.out.println(session.getAttribute("username")+" logged In");
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("User "+loggedInUser.getUserName()+" logged In",userDto));
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("Invalid Credentials",null));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("User "+
		session.getAttribute("username")+" already logged in",null));
	}
	
	@GetMapping
	public List<User> getAllUsers(){
//		session.setAttribute("email", userDto.getEmail());
//		session.setAttribute("username", userService.getUser(userDto.getEmail()).getUserName());
		return userService.getAllUsers();
	}
	
	@GetMapping("/logout")
	public ResponseEntity<ResponseDto> logout(HttpSession session){
		String userName=(String) session.getAttribute("username");
		if(userName!=null){
			session.removeAttribute("email");
			session.removeAttribute("username");
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("User "+userName+" logged out",null));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("No User Logged In",null));
	}
	
	@PostMapping("/register")
	public ResponseEntity<ResponseDto> register(@RequestBody User user){
		//session.setAttribute("email", user.getEmail());
		User newUser= userService.createUser(user);
		if(newUser!=null){
			//session.setAttribute("username", loggedInUser.getUserName());
			//System.out.println(session.getAttribute("username")+" logged In");
			return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto("New User "+newUser.getUserName()+" Created",newUser));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("User Already exists with this email id: "+user.getEmail(),null));
	}
	
	@PutMapping("/lovedBooks/{bookId}")
	public ResponseEntity<ResponseDto> addToLovedBooks(@PathVariable int bookId, HttpSession session){
		String email=(String) session.getAttribute("email");
		if(email!=null){
			User user= userService.getUser(email);
			if(user!=null){
				user.getLovedBooks().add(bookService.getBooksByid(bookId));
				User updateduser= userService.updateUser(user);
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseDto(bookService.getBooksByid(bookId).getBookTitle()+
								" is added to loved books of user "+user.getUserName(),updateduser));
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(new ResponseDto(bookService.getBooksByid(bookId).getBookTitle()+
					" can not be added to loved books of user "+email,null));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("User is not logged in",null));
		
	}
	@PutMapping("/readLaterBooks/{bookId}")
	public ResponseEntity<ResponseDto> addToReadLaterBooks(@PathVariable int bookId, HttpSession session){
		String email=(String) session.getAttribute("email");
		if(email!=null){
			User user= userService.getUser(email);
			if(user!=null){
				user.getReadLaterBooks().add(bookService.getBooksByid(bookId));
				User updateduser= userService.updateUser(user);
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseDto(bookService.getBooksByid(bookId).getBookTitle()+
								" is added to read later books of user "+user.getUserName(),updateduser));
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(new ResponseDto(bookService.getBooksByid(bookId).getBookTitle()+
					" can not be added to read Later books of user "+email,null));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("User is not logged in",null));
		
	}
	
	@GetMapping("/lovedBooks")
	public ResponseEntity<ResponseDto> viewLovedBooks(HttpSession session){
		String email=(String) session.getAttribute("email");
		if(email!=null){
			User user= userService.getUser(email);
			if(user!=null){
				List<Book> lovedBooks=  user.getLovedBooks();
				//User updateduser= userService.updateUser(user);
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseDto("The Loved Books of user: "+user.getUserName(),lovedBooks));
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(new ResponseDto("No User with the email id: "+email,null));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("User is not logged in",null));
		
	}
	@GetMapping("/readLaterBooks")
	public ResponseEntity<ResponseDto> viewReadLaterBooks(HttpSession session){
		String email=(String) session.getAttribute("email");
		if(email!=null){
			User user= userService.getUser(email);
			if(user!=null){
				List<Book> readLaterBooks=  user.getReadLaterBooks();
				//User updateduser= userService.updateUser(user);
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseDto("The Read later Books of user: "+user.getUserName(),readLaterBooks));
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(new ResponseDto("No User with the email id: "+email,null));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("User is not logged in",null));
		
	}


}