/**
 * 
 */
package com.briandutremble.birds.errorhandler;



@SuppressWarnings("serial")
public class FieldValidationException extends RuntimeException {

  public FieldValidationException() {}

  
  public FieldValidationException(String message) {
    super(message);
  }

  public FieldValidationException(Throwable cause) {
    super(cause);
  }

  public FieldValidationException(String message, Throwable cause) {
    super(message, cause);
  }
}
