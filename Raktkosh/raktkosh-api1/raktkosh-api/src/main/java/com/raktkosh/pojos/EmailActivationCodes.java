package com.raktkosh.pojos;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "email_activation_codes")
//@NoArgsConstructor
//@AllArgsConstructor
//@Getter
//@Setter
@ToString
public class EmailActivationCodes extends BaseEntity {
  
  public EmailActivationCodes(String email, String code, LocalDateTime validity, int otp) {
		super();
		this.email = email;
		this.code = code;
		this.validity = validity;
		this.otp = otp;
	}


@Column(length = 50, nullable = false)
  private String email;
  
  @Column(nullable = false)
  private String code;
  
  @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
  @Column(nullable = false)
  private LocalDateTime validity;
  
  
  private int otp;


public EmailActivationCodes(String email, String code, LocalDateTime validity) {
	super();
	this.email = email;
	this.code = code;
	this.validity = validity;
}


public String getEmail() {
	return email;
}


public void setEmail(String email) {
	this.email = email;
}


public String getCode() {
	return code;
}


public void setCode(String code) {
	this.code = code;
}


public LocalDateTime getValidity() {
	return validity;
}


public void setValidity(LocalDateTime validity) {
	this.validity = validity;
}


public int getOtp() {
	return otp;
}


public void setOtp(int otp) {
	this.otp = otp;
}
}
