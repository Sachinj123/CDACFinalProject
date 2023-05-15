package com.raktkosh.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raktkosh.enums.Role;
import com.raktkosh.pojos.User;
import com.raktkosh.services.IAdminService;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = { "${com.raktkosh.ORIGINS}" })
public class AdminRestController {
	@Autowired
	private IAdminService adminSevice;
	
	public AdminRestController() {
		System.out.println("in constr of "+getClass().getName());
		
	}
	
	@GetMapping("/users")
	public List<User> getAllUsers() {
		return adminSevice.featchAllUsers();
	}
	
	@GetMapping("/role_admin")
	public List<User> findByRoleA(){
	return adminSevice.findByRole(Role.ADMIN);
	}


	@GetMapping("/role_users")
	public List<User> findByRoleU(){
	return adminSevice.findByRole(Role.USER);
	}
	
	@PatchMapping("/toggle_activation/{id}")
	public ResponseEntity<?> updateUserActivation(@PathVariable Long id) {
		return ResponseEntity.ok(adminSevice.updateActivatedById(id));
	}
	
	//for suspend user list
	@GetMapping("/show_suspended")
	public List<User>findActivatedF(){
		return adminSevice.findByActivated(false);
	}
	
	//for active user list
		@GetMapping("/show_active")
		public List<User>findActivatedT(){
			return adminSevice.findByActivated(true);
		}
	

	
}
