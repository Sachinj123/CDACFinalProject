package com.raktkosh.controllers;

import javax.mail.MessagingException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raktkosh.dao.IBDCampRepo;
import com.raktkosh.dao.UserAddressRepository;
import com.raktkosh.dao.UserRepository;
import com.raktkosh.dto.BDCampDto;
import com.raktkosh.dto.MessageResponse;
import com.raktkosh.enums.Role;
import com.raktkosh.pojos.BDCamp;
import com.raktkosh.pojos.BloodBank;
import com.raktkosh.pojos.User;
import com.raktkosh.pojos.UserAddress;
import com.raktkosh.services.IBDCampService;

@RestController
@RequestMapping("/camp")
@CrossOrigin(origins = { "${com.raktkosh.ORIGINS}" })

public class BDCampController {
	
	@Autowired
	private IBDCampService campService;

	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private IBDCampRepo campRepo;
	
	@Autowired
	private UserAddressRepository addRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	//fetching list of all camps
	@GetMapping("/")
	public ResponseEntity<?> getAllCamps() {
		return ResponseEntity.status(HttpStatus.OK).body(campService.getAllCamps());
	}

	//validating & adding/saving a new camp
	@PostMapping("/add")
	public ResponseEntity<?> saveCamp(@RequestBody BDCampDto dto) {
		//validating userName
		if (userRepository.existsByUsername(dto.getUsername())) {
			return ResponseEntity.badRequest().body("Username is already taken.");
		}
		
        //validating email
		if (userRepository.existsByEmail(dto.getEmail())) {
			return ResponseEntity.badRequest().body("Email is already in use.");
		}

		//setting password, role & activated-true
		dto.setPassword(passwordEncoder.encode(dto.getPassword()));
		User user = mapper.map(dto, User.class);
		user.setRole(Role.CAMP);
		user.setActivated(true);
		
        //mapping address,camp & setting user to address 
        UserAddress address = mapper.map(dto, UserAddress.class);
		BDCamp camp = mapper.map(dto, BDCamp.class);
		address.setUser(user);
		
		// camp.setId(null);
		System.out.println(user.getId());
		
		addRepo.save(address);
		User use = userRepository.save(user);
		camp.setUserId(use.getId());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(campRepo.save(camp));
	}

	//deleting camp 
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteCamp(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(campService.deleteCampById(id));
	}

	//editing camp details 
	@PostMapping("/update/{id}")
	public ResponseEntity<?> updateCamp(@PathVariable Long id, @RequestBody BDCampDto dto) {
		BDCamp camp = mapper.map(dto, BDCamp.class);
		return ResponseEntity.status(HttpStatus.OK).body(campService.updateCampById(id, camp));
	}

	//register user to upcoming camps
	@GetMapping("/addUser/{campid}/{id}")
	public ResponseEntity<?> registerUserForCamp(@PathVariable Long id, @PathVariable Long campid) {
		// BDCamp camp = mapper.map(dto, BDCamp.class);
		return ResponseEntity.status(HttpStatus.OK).body(campService.registerUserForCamp(id, campid));
	}

}
