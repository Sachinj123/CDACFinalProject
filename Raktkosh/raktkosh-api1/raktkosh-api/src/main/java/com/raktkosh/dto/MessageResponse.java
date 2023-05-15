package com.raktkosh.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MessageResponse {
  private String message;

public String getMessage() {
	return message;
}

public void setMessage(String message) {
	this.message = message;
}
}
