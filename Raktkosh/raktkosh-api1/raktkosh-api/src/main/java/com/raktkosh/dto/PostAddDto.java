package com.raktkosh.dto;


import java.time.LocalDateTime;

import com.raktkosh.enums.Antigens;
import com.raktkosh.enums.BloodTypes;
import com.raktkosh.enums.PostCategory;
import com.raktkosh.pojos.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


//@NoArgsConstructor
//@AllArgsConstructor
//@Getter
//@Setter
//@ToString(exclude="userId")
public class PostAddDto {
	public PostAddDto(Long id, Antigens antigen, LocalDateTime createdOn, PostCategory postCategory, BloodTypes type,
			User userId, String state, String district, String city, String locality, String zip) {
		super();
		this.id = id;
		this.antigen = antigen;
		this.createdOn = createdOn;
		this.postCategory = postCategory;
		this.type = type;
		this.userId = userId;
		this.state = state;
		this.district = district;
		this.city = city;
		this.locality = locality;
		this.zip = zip;
	}

	// post
	private Long id;

	private Antigens antigen;

	private LocalDateTime createdOn;

	private PostCategory postCategory;

	private BloodTypes type;

	private User userId;

	private String state;

	private String district;

	private String city;

	private String locality;
	
	private String zip;

	@Override
	public String toString() {
		return "PostAddDto [id=" + id + ", antigen=" + antigen + ", createdOn=" + createdOn + ", postCategory="
				+ postCategory + ", type=" + type + ", state=" + state + ", district=" + district + ", city=" + city
				+ ", locality=" + locality + ", zip=" + zip + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Antigens getAntigen() {
		return antigen;
	}

	public void setAntigen(Antigens antigen) {
		this.antigen = antigen;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public PostCategory getPostCategory() {
		return postCategory;
	}

	public void setPostCategory(PostCategory postCategory) {
		this.postCategory = postCategory;
	}

	public BloodTypes getType() {
		return type;
	}

	public void setType(BloodTypes type) {
		this.type = type;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
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

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	
}
