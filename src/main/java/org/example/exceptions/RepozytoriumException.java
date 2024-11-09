package org.example.exceptions;

public class RepozytoriumException extends RuntimeException {

  public RepozytoriumException(String message) {
    super(message);
  }
  public RepozytoriumException(String message, Throwable cause) {
    super(message, cause);
  }
}
