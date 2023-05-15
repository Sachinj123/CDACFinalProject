package com.raktkosh.pojos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.raktkosh.dto.SignupDTO;
import com.raktkosh.enums.Antigens;
import com.raktkosh.enums.BloodTypes;
import com.raktkosh.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
//@Getter
//@Setter
@ToString(exclude="address")
public class User extends BaseEntity {
  
  public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public LocalDate getDob() {
		return dob;
	}


	public void setDob(LocalDate dob) {
		this.dob = dob;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public Role getRole() {
		return role;
	}


	public void setRole(Role role) {
		this.role = role;
	}


	public BloodTypes getBloodType() {
		return bloodType;
	}


	public void setBloodType(BloodTypes bloodType) {
		this.bloodType = bloodType;
	}


	public Antigens getAntigen() {
		return antigen;
	}


	public void setAntigen(Antigens antigen) {
		this.antigen = antigen;
	}


	public LocalDateTime getRegisterdOn() {
		return registerdOn;
	}


	public void setRegisterdOn(LocalDateTime registerdOn) {
		this.registerdOn = registerdOn;
	}


	public UserAddress getAddress() {
		return address;
	}


	public void setAddress(UserAddress address) {
		this.address = address;
	}


	public boolean isActivated() {
		return activated;
	}


	public void setActivated(boolean activated) {
		this.activated = activated;
	}


	public LocalDate getDonationDate() {
		return donationDate;
	}


	public void setDonationDate(LocalDate donationDate) {
		this.donationDate = donationDate;
	}


	public List<Post> getUserId() {
		return userId;
	}


	public void setUserId(List<Post> userId) {
		this.userId = userId;
	}


@Column(unique = true, length = 20, nullable = false)
  private String username;
  
  @Column(nullable = false)
  private String password;
  
  @Column(length = 50, nullable = false)
  private String name;
  
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @Column
  private LocalDate dob;
  
  @Column(length = 50, unique = true, nullable = false)
  private String email;
  
  //@Pattern(regexp = "^[6789]\\d{9}$")
  @Column(length = 10)
  private String mobile;
  
  @Enumerated(value = EnumType.STRING)
  @Column(length = 10)
  private Role role;
  
  @Enumerated(value = EnumType.STRING)
  @Column(name = "blood_type", length = 2)
  private BloodTypes bloodType;
  
  @Enumerated(value = EnumType.STRING)
  @Column(length = 10)
  private Antigens antigen;
  
  @Column(insertable = false, updatable = false, columnDefinition = "DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP", name = "registered_on")
  private LocalDateTime registerdOn;
 
  @OneToOne(mappedBy = "user",  cascade = CascadeType.ALL)
  @JsonIgnore
  private UserAddress address;
  
  @Column(insertable = false, columnDefinition = "TINYINT NOT NULL DEFAULT 0")
  private boolean activated;
  
  @Column(name="recent_donation_date")
  private LocalDate donationDate;
  
  
  @JsonIgnore
  @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
  private List<Post> userId;



}
