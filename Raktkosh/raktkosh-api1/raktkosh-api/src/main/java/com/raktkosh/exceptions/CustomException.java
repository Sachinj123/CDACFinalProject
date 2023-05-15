package com.raktkosh.exceptions;

public class CustomException extends RuntimeException {
  private static final long serialVersionUID = 53L;

  public CustomException(String message) {
    super(message);
  }
}
