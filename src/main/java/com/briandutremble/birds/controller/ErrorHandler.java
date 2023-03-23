/**
 * 
 */
package com.briandutremble.birds.controller;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import com.briandutremble.birds.errorhandler.DeleteBirdException;
import com.briandutremble.birds.entity.AddBirdRequest;
import com.briandutremble.birds.errorhandler.AlreadyExistsException;
import com.briandutremble.birds.errorhandler.DeleteBirdException;
import com.briandutremble.birds.errorhandler.FieldValidationException;
import lombok.extern.slf4j.Slf4j;


@RestControllerAdvice
@Slf4j
public class ErrorHandler {

  
  private enum LogStatus {
    STACK_TRACE, MESSAGE_ONLY
  }

  @ExceptionHandler(DeleteBirdException.class)
  @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
  public Map<String, Object> handleDeleteBreedException(DeleteBirdException e,
      WebRequest webRequest) {
    return createExceptionMessage(e, HttpStatus.INTERNAL_SERVER_ERROR, webRequest,
        LogStatus.MESSAGE_ONLY);
  }

  @ExceptionHandler(DuplicateKeyException.class)
  @ResponseStatus(code = HttpStatus.CONFLICT)
  public Map<String, Object> handleDuplicateKeyException(DuplicateKeyException e,
      WebRequest webRequest) {
    String message = "Duplicate key";

    if (Objects.nonNull(e.getCause())
        && e.getCause() instanceof SQLIntegrityConstraintViolationException violationException) {
      message = violationException.getMessage();
    }

    Exception alt = new AlreadyExistsException(message);
    return createExceptionMessage(alt, HttpStatus.CONFLICT, webRequest, LogStatus.MESSAGE_ONLY);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(code = HttpStatus.BAD_REQUEST)
  public Map<String, Object> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e, WebRequest webRequest) {

    String errorFieldNames = e.getBindingResult()
        .getFieldErrors() // @formatter:off
        .stream()
        .map(FieldError::getField)
        .collect(Collectors.joining(", ")); // @formatter:on

    Exception alt = new FieldValidationException("Invalid field(s): " + errorFieldNames);
    return createExceptionMessage(alt, HttpStatus.BAD_REQUEST, webRequest, LogStatus.MESSAGE_ONLY);
  }

 
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  @ResponseStatus(code = HttpStatus.BAD_REQUEST)
  public Map<String, Object> handleMethodArgumentTypeMismatchException(
      MethodArgumentTypeMismatchException e, WebRequest webRequest) {
    return createExceptionMessage(e, HttpStatus.BAD_REQUEST, webRequest, LogStatus.MESSAGE_ONLY);
  }

  @ExceptionHandler(NoSuchElementException.class)
  @ResponseStatus(code = HttpStatus.NOT_FOUND)
  public Map<String, Object> handleNoSuchElementException(NoSuchElementException e,
      WebRequest webRequest) {
    return createExceptionMessage(e, HttpStatus.NOT_FOUND, webRequest, LogStatus.MESSAGE_ONLY);
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
  public Map<String, Object> handleException(Exception e, WebRequest webRequest) {
    return createExceptionMessage(e, HttpStatus.INTERNAL_SERVER_ERROR, webRequest,
        LogStatus.STACK_TRACE);
  }

  private Map<String, Object> createExceptionMessage(Exception e, HttpStatus status,
      WebRequest webRequest, LogStatus logStatus) {
    Map<String, Object> error = new HashMap<>();
    String timestamp = ZonedDateTime.now().format(DateTimeFormatter.RFC_1123_DATE_TIME);

    if (webRequest instanceof ServletWebRequest servletWebRequest) {
      error.put("uri", servletWebRequest.getRequest().getRequestURI());
    }

    error.put("message", e.toString());
    error.put("status code", status.value());
    error.put("timestamp", timestamp);
    error.put("reason", status.getReasonPhrase());

    if (logStatus == LogStatus.MESSAGE_ONLY) {
      log.error("Exception: {}", e.toString());
    } else {
      log.error("Exception:", e);
    }

    return error;
  }
}
