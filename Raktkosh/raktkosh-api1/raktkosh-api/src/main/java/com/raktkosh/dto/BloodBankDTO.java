package com.raktkosh.dto;

import java.time.LocalTime;

import org.hibernate.validator.constraints.Length;

//import com.raktkosh.pojos.BankAddress;
import com.raktkosh.pojos.BloodBank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
//@Getter
//@Setter
public class BloodBankDTO extends SignupDTO {

	@Length(min = 10, max = 10)
	private String mobile;
	private LocalTime openAt;
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public LocalTime getOpenAt() {
		return openAt;
	}
	public void setOpenAt(LocalTime openAt) {
		this.openAt = openAt;
	}
	public LocalTime getCloseAt() {
		return closeAt;
	}
	public void setCloseAt(LocalTime closeAt) {
		this.closeAt = closeAt;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getLocality() {
		return locality;
	}
	public void setLocality(String locality) {
		this.locality = locality;
	}
	private LocalTime closeAt;

	private String state;
	private String district;
	private String city;
	@Length(min = 6, max = 6)
	private String zip;
	private String locality;

}
