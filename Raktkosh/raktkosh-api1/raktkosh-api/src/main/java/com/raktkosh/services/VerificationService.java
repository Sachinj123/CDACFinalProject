package com.raktkosh.services;

import java.time.LocalDateTime;
import java.util.Random;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.raktkosh.components.EMailSender;
import com.raktkosh.dao.EmailActivationRepository;
import com.raktkosh.dao.UserRepository;
import com.raktkosh.exceptions.ActivationException;
import com.raktkosh.pojos.EmailActivationCodes;
import com.raktkosh.pojos.User;
import com.raktkosh.utils.JWTUtils;

import net.bytebuddy.utility.RandomString;

@Service
@Transactional
public class VerificationService implements IVerificationService {

	@Autowired
	private EmailActivationRepository emailRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private PasswordEncoder hash;

	@Value("${com.raktkosh.JWT_VALIDITY}")
	private int validity;

	@Autowired
	private EMailSender emailSender;

	@Autowired
	private JWTUtils jwtUtils;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void sendVerificationMail(String email) throws MessagingException {
		// String code = hash.encode(RandomString.make(64));
		String code = RandomString.make(64);
		LocalDateTime expiry = LocalDateTime.now();
		emailRepo.save(new EmailActivationCodes(email, code, expiry));
		emailSender.sendVerificationEmail(email, code);
	}
 
	//Verifying the user request
	@Override
	public User verifyEmail(String token) {
		
		//finding user mail by token 
		EmailActivationCodes code = emailRepo.findByCode(token)
				.orElseThrow(() -> new ActivationException("Invalid Code"));
		User user = userRepo.findByEmail(code.getEmail()).orElseThrow(() -> new ActivationException("User not found"));
		//setting isActivated flag to true
		user.setActivated(true);
		userRepo.save(user);
		emailRepo.deleteById(code.getId());
		return user;
	}

	@Override
	public void sendOTPToChangePassword(String email) throws MessagingException {
		// ????
		Random rand = new Random();
		// Generating four digit One Time Password (OTP) for the user
		int otp = (int) (Math.random() * 9000) + 1000;

		String code = RandomString.make(12);

		LocalDateTime expiry = LocalDateTime.now();
		// Storing verification (OTP) details in database
		emailRepo.save(new EmailActivationCodes(email, code, expiry, otp));
		// sending verification OTP
		emailSender.resetPasswordEmail(email, otp);
	}

	@Override
	public void changePassword(String email, String otp, String confirmpassword) {
		// find otp for given mail id
		EmailActivationCodes code = emailRepo.findByEmail(email)
				.orElseThrow(() -> new ActivationException("Invalid Code"));
		// validating user otp and otp in database
		if (code.getOtp() == Integer.parseInt(otp)) {
			// find user with
			User user = userRepo.findByEmail(code.getEmail())
					.orElseThrow(() -> new ActivationException("User not found"));
			// saving new bcrypt password in database
			user.setPassword(passwordEncoder.encode(confirmpassword));
			userRepo.save(user);
			// deleting entry from database so cant use same code again to change password
			emailRepo.deleteById(code.getId());
		} else {
			throw new ActivationException("OTP not matching");
		}

	}
}
