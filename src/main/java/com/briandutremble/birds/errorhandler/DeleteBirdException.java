/**
 * 
 */
package com.briandutremble.birds.errorhandler;


@SuppressWarnings("serial")
public class DeleteBirdException extends RuntimeException {

 
  public DeleteBirdException() {}


  public DeleteBirdException(String message) {
    super(message);
  }


  public DeleteBirdException(Throwable cause) {
    super(cause);
  }

  public DeleteBirdException(String message, Throwable cause) {
    super(message, cause);
  }
}
