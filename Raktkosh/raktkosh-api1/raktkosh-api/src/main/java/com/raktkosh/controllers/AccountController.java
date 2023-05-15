package com.raktkosh.controllers;

import java.rmi.activation.ActivationException;
import java.sql.SQLException;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raktkosh.dao.UserRepository;
import com.raktkosh.dto.BloodBankDTO;
import com.raktkosh.dto.MessageResponse;
import com.raktkosh.dto.NewPasswordDTO;
import com.raktkosh.dto.PasswordResetDTO;
import com.raktkosh.dto.SigninDTO;
import com.raktkosh.exceptions.ResourceNotFoundException;
import com.raktkosh.pojos.User;
import com.raktkosh.security.UserDetailsImpl;
import com.raktkosh.services.IAccountService;
import com.raktkosh.services.IVerificationService;
import com.raktkosh.services.UserDetailsServiceImpl;
import com.raktkosh.utils.JWTUtils;

@RestController
@RequestMapping("/account")
@CrossOrigin(origins = { "${com.raktkosh.ORIGINS}" })
public class AccountController {

	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserDetailsServiceImpl userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private IVerificationService verification;

	@Autowired
	private JWTUtils jwtUtils;

	@Autowired
	private IAccountService accountService;

	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody @Valid BloodBankDTO signupRequest) throws SQLException {
		try {
			// signing up user on the basis of its role
			accountService.userSignUp(signupRequest);
		} catch (ActivationException e) {
//			e.printStackTrace();
//			return ResponseEntity.badRequest().body(new MessageResponse("Email Or Username Already exist"));

		} catch (MessagingException e) {
			//return ResponseEntity.badRequest().body(new MessageResponse("Email Or Username Already exist"));
		}
		return ResponseEntity.ok(HttpStatus.OK);
	}

	
	
	// add a method to authenticate user . Incase of success --send back token , o.w
	// send back err mesg
	@PostMapping("/signin")
	public ResponseEntity<?> signup(@RequestBody @Valid SigninDTO signinRequest) {
		// Signin DTO contains login credentials
		//ip-->unauthentic user
		//op--> authenticated user returns authenticate principal
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(signinRequest.getUsername(), signinRequest.getPassword()));
		
		// setting authentication information in SecurityContext with the current
		// execution thread
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		// generating jwt token for authenticated user
		String jwt = jwtUtils.generateJWTToken(authentication);
		
		// setting jwt token to the authenticated user
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		userDetails.setToken(jwt);

		return ResponseEntity.ok(userDetails);
	}
	
	
	
    //After successfull Signup user recieves a mail containing url + token
	//After clicking on that link we are setting isActivated flag to true and navigating user to singin page
	
	@GetMapping("/verify/{token}")
	public ResponseEntity<?> verify(@PathVariable String token) {
		return ResponseEntity.ok(verification.verifyEmail(token));
	}

	
	
	
	// fetching user info (profile) on the basis of jwt_token
	@GetMapping("/info/{token}")
	public ResponseEntity<?> getUserInfo(@PathVariable String token) {
		if (token != null && jwtUtils.isValidToken(token)) {
			String username = jwtUtils.getUsernameFromToken(token);
			UserDetails userDetails = userService.loadUserByUsername(username);
			System.out.println(userDetails.getUsername());
			System.out.println(userDetails.toString());
			return ResponseEntity.ok(userDetails);
		}
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("invalid message");
	}

	//Method for sending OTP via mail to user
	@PostMapping("/forgotpassword")
	public ResponseEntity<?> getUserInfo(@RequestBody PasswordResetDTO dto) throws MessagingException {
		if (userRepository.existsByUsername(dto.getUsername())) {
			User user = userRepository.findByUsername(dto.getUsername())//finding user by provided username
					.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
			verification.sendOTPToChangePassword(user.getEmail());
		}
		return null;
	}

	// setting up new password
	@PostMapping("/resetpassword")
	public ResponseEntity<?> getNewPassword(@RequestBody NewPasswordDTO dto) throws MessagingException {
		if (dto.getConfirmpassword().equals(dto.getNewpassword())) {
			//check if user exists in userRepo by userName 
			if (userRepository.existsByUsername(dto.getUsername())) {
				User user = userRepository.findByUsername(dto.getUsername())
						.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
				//validating user email, otp then saving new pass in db & deleting entry in emilRepo 
				verification.changePassword(user.getEmail(), dto.getOtp(), dto.getConfirmpassword());
			}
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("Password reseted successfully");
					
		}

		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Password not matching or invalid OTP");
				
	}

	// fetching user details for profile page
	@GetMapping("/profile/{id}")
	public ResponseEntity<?> getProfile(@PathVariable Long id) {
		return ResponseEntity.ok(userRepository.findById(id));
	}
}
