package br.com.anderson.blog.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@RestControllerAdvice
public class RestControllerExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ProblemDetails> handleException(Exception exception) {
    exception.printStackTrace();

    ProblemDetails problem = new ProblemDetails()
      .setMessage(exception.getMessage())
      .setDetail(Arrays.toString(exception.getStackTrace()))
      .setStatus(HttpStatus.INTERNAL_SERVER_ERROR);

    return ResponseEntity.internalServerError()
      .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PROBLEM_JSON_VALUE)
      .body(problem);
  }

}
