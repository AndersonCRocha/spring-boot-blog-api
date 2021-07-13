package br.com.anderson.blog.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;

@RestControllerAdvice
public class RestControllerExceptionHandler {

  @ExceptionHandler(InvalidRefreshTokenException.class)
  public ResponseEntity<ProblemDetails> handleInvalidRefreshTokenException(InvalidRefreshTokenException exception) {
    ProblemDetails problem = this.buildProblemDetails(HttpStatus.BAD_REQUEST, exception.getMessage());
    return new ResponseEntity<>(problem, this.getDefaultHeaders(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ProblemDetails> handleEntityNotFoundException(EntityNotFoundException exception) {
    ProblemDetails problem = this.buildProblemDetails(HttpStatus.NOT_FOUND, exception.getMessage());
    return new ResponseEntity<>(problem, this.getDefaultHeaders(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ProblemDetails> handleAccessDeniedException(AccessDeniedException exception) {
    ProblemDetails problem = this.buildProblemDetails(
      HttpStatus.FORBIDDEN, exception.getMessage(), Arrays.toString(exception.getStackTrace())
    );
    return new ResponseEntity<>(problem, this.getDefaultHeaders(), HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(UserAlreadyExistsException.class)
  public ResponseEntity<ProblemDetails> handleUserAlreadyExistsException(UserAlreadyExistsException exception) {
    ProblemDetails problem = this.buildProblemDetails(HttpStatus.CONFLICT, exception.getMessage());
    return new ResponseEntity<>(problem, this.getDefaultHeaders(), HttpStatus.CONFLICT);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ProblemDetails> handleException(Exception exception) {
    ProblemDetails problem = this.buildProblemDetails(
      HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), Arrays.toString(exception.getStackTrace())
    );

    return ResponseEntity.internalServerError()
      .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PROBLEM_JSON_VALUE)
      .body(problem);
  }

  private HttpHeaders getDefaultHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_PROBLEM_JSON);
    return headers;
  }

  private ProblemDetails buildProblemDetails(HttpStatus status, String message) {
    return this.buildProblemDetails(status, message, message);
  }

  private ProblemDetails buildProblemDetails(HttpStatus status, String message, String detail) {
    return new ProblemDetails()
      .setMessage(message)
      .setDetail(detail)
      .setStatus(status);
  }
}
