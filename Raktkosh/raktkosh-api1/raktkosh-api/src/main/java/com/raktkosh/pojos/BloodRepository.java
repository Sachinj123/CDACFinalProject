package com.raktkosh.pojos;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.raktkosh.dto.BloodBankRepositoryDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "blood_repository")
//@NoArgsConstructor
//@AllArgsConstructor
//@Getter
//@Setter
@ToString
public class BloodRepository {
  
  public BloodRepository(BloodRepositoryID id, int availability) {
		super();
		this.id = id;
		this.availability = availability;
	}

@EmbeddedId
  private BloodRepositoryID id;
    
  @Column(name = "availability")
  private int availability;
  
  public static BloodRepository build(BloodBankRepositoryDTO repo) {
    BloodBank bank = new BloodBank();
    bank.setId(repo.getBankId());
    return new BloodRepository(new BloodRepositoryID(repo.getType(), repo.getAntigen(), bank), repo.getAvailability());
  }

public BloodRepositoryID getId() {
	return id;
}

public void setId(BloodRepositoryID id) {
	this.id = id;
}

public int getAvailability() {
	return availability;
}

public void setAvailability(int availability) {
	this.availability = availability;
}
}
