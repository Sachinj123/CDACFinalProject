package com.raktkosh.dto;

import com.raktkosh.enums.Antigens;
import com.raktkosh.enums.BloodTypes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
//@Getter
//@Setter
@ToString
public class BloodBankRepositoryIdDTO {
  public Long getBankId() {
		return bankId;
	}
	public void setBankId(Long bankId) {
		this.bankId = bankId;
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
protected Long bankId;
  protected BloodTypes type;
  protected Antigens antigen;
}
