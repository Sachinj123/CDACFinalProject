package com.raktkosh.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//@NoArgsConstructor
//@AllArgsConstructor

@ToString
public class ErrorResponse {
  public ErrorResponse(String message, LocalDateTime timestamp) {
		super();
		this.message = message;
		this.timestamp = timestamp;
	}
public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
private String message;
  private LocalDateTime timestamp;
}
