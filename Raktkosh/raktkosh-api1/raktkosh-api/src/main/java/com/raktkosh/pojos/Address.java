package com.raktkosh.pojos;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Address extends BaseEntity {
  
  @Column(length = 30)
  private String state;
  
  @Column(length = 30)
  private String district;
  
  @Column(length = 30)
  private String city;
  
  @Pattern(regexp = "\\d{6}")
  @Column(length = 6)
  private String zip;
  
  @Column(length = 60)
  private String locality;

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
  
 
}
