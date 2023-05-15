package com.raktkosh.controllers;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.raktkosh.dao.BankRepository;
import com.raktkosh.dao.UserAddressRepository;
import com.raktkosh.dao.UserRepository;
import com.raktkosh.dto.BloodBankDTO;
import com.raktkosh.dto.MessageResponse;
import com.raktkosh.enums.Role;
import com.raktkosh.exceptions.ResourceNotFoundException;
import com.raktkosh.pojos.BloodBank;
import com.raktkosh.pojos.User;
import com.raktkosh.pojos.UserAddress;
import com.raktkosh.services.IBloodBankService;

@RestController
@RequestMapping("/bloodbank")
@CrossOrigin(origins = { "${com.raktkosh.ORIGINS}" })
public class BloodBankController {

	@Autowired
	private IBloodBankService bankService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private UserAddressRepository addRepo;

	@Autowired
	private BankRepository bankRepo;

	@GetMapping("/list")
	public ResponseEntity<?> getAllBanks() {
		return ResponseEntity.ok(bankService.getAllBanks());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getBanksDetails(@PathVariable Long id) {
		return ResponseEntity.ok(bankService.getBankDetailsById(id));
	}

	// adding new blood bank
	@PostMapping("/add")
	public ResponseEntity<?> addBanks(@RequestBody @Valid BloodBankDTO dto) {
//    //checking user by userName in userRepo
		if (userRepository.existsByUsername(dto.getUsername())) {
			return ResponseEntity.badRequest().body("Username is already taken.");
		}

		// checking user by email in userRepo
		if (userRepository.existsByEmail(dto.getEmail())) {
			return ResponseEntity.badRequest().body("Email is already in use.");
		}
		// setting and encrypting password in db
		dto.setPassword(passwordEncoder.encode(dto.getPassword()));
		User user = mapper.map(dto, User.class);
		user.setRole(Role.BLOOD_BANK); // setting role
		user.setActivated(true);

		// mapping address and bank
		UserAddress address = mapper.map(dto, UserAddress.class);
		BloodBank bank = mapper.map(dto, BloodBank.class);

		// setting user to address
		address.setUser(user);
		System.out.println(user.getId());

		// saving address and user with respective repo
		addRepo.save(address);
		User use = userRepository.save(user);
		// set id to bank
		bank.setUserId(use.getId());

		return ResponseEntity.status(HttpStatus.CREATED).body(bankRepo.save(bank));
	}

	// deleting blood bank by id
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteBank(@PathVariable Long id) {
		System.out.println("Bank ID : " + id);
		return ResponseEntity.ok(bankService.deleteById(id));
	}

	// updating/editing bloodbank details
	@PostMapping("/update/{id}")
	public ResponseEntity<?> updateBank(@PathVariable Long id, @RequestBody BloodBankDTO dto) {
		// BloodBank bank = mapper.map(dto, BloodBank.class);
		User user = mapper.map(dto, User.class);
		user.setId(id);
		user.setActivated(true);
		userRepository.save(user);
		BloodBank bank2 = bankRepo.findByUserId(id)
				.orElseThrow(() -> new ResourceNotFoundException("Entity Not found"));
		bank2.setOpenAt(dto.getOpenAt());
		bank2.setCloseAt(dto.getCloseAt());
		bank2.setMobile(dto.getMobile());
		bank2.setEmail(dto.getEmail());
		return ResponseEntity.ok(bankRepo.save(bank2));
	}
}
