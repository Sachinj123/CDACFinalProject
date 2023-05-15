package com.raktkosh.pojos;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.raktkosh.dto.BloodBankRepositoryIdDTO;
import com.raktkosh.enums.Antigens;
import com.raktkosh.enums.BloodTypes;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Embeddable
//@NoArgsConstructor
//@AllArgsConstructor
//@Getter
//@Setter
@EqualsAndHashCode
@ToString
public class BloodRepositoryID implements Serializable {

  public BloodRepositoryID(BloodTypes type, Antigens antigen, BloodBank bank) {
		super();
		this.type = type;
		this.antigen = antigen;
		this.bank = bank;
	}

private static final long serialVersionUID = 7L;

  @Enumerated(value = EnumType.STRING)
  @Column(length = 2)
  private BloodTypes type;
  
  @Enumerated(value = EnumType.STRING)
  @Column(length = 10)
  private Antigens antigen;
  
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "bank")
  private BloodBank bank;
  
  public static BloodRepositoryID build (BloodBankRepositoryIdDTO repo) {
    BloodBank bank = new BloodBank();
    bank.setId(repo.getBankId());
    return new BloodRepositoryID(repo.getType(), repo.getAntigen(), bank);
  }

public BloodTypes getType() {
	return type;
}

public void setType(BloodTypes type) {
	this.type = type;
}

public Antigens getAntigen() {
	return antigen;
}

public void setAntigen(Antigens antigen) {
	this.antigen = antigen;
}

public BloodBank getBank() {
	return bank;
}

public void setBank(BloodBank bank) {
	this.bank = bank;
}

public static long getSerialversionuid() {
	return serialVersionUID;
}
}
