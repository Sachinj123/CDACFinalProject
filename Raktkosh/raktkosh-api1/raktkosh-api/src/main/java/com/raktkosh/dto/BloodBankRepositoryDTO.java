package com.raktkosh.dto;

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
public class BloodBankRepositoryDTO extends BloodBankRepositoryIdDTO {
  public int getAvailability() {
		return availability;
	}

	public void setAvailability(int availability) {
		this.availability = availability;
	}

private int availability;
}
