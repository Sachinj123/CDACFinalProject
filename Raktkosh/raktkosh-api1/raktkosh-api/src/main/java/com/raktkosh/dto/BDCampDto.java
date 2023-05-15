package com.raktkosh.dto;

import java.time.LocalDate;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
//@Getter
//@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class BDCampDto extends SignupDTO{
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getCampDate() {
		return campDate;
	}
	public void setCampDate(LocalDate campDate) {
		this.campDate = campDate;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getLocality() {
		return locality;
	}
	public void setLocality(String locality) {
		this.locality = locality;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	private Long id;
	@NotBlank
	private String name;
//	@NotBlank
	@Future
	private LocalDate campDate;
	//@NotBlank
	private String city;
	//@NotBlank
	private String locality;
	//@NotBlank
	private String district;
//	@NotBlank
	private String zip;
	//@NotBlank
	private String startTime;
//	@NotBlank
	private String endTime;
	
}
