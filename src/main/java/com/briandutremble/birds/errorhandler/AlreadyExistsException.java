/**
 * 
 */
package com.briandutremble.birds.errorhandler;



@SuppressWarnings("serial")
public class AlreadyExistsException extends RuntimeException {

 
  public AlreadyExistsException() {}

 
  public AlreadyExistsException(String message) {
    super(message);
  }


  public AlreadyExistsException(Throwable cause) {
    super(cause);
  }

  public AlreadyExistsException(String message, Throwable cause) {
    super(message, cause);
  }
}
