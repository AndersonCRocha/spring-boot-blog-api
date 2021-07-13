package br.com.anderson.blog.exceptions;

import br.com.anderson.blog.security.exception.InvalidCredentialsException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestControllerExceptionHandler {

  private final MessageSource messageSource;

  public RestControllerExceptionHandler(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  @ExceptionHandler(BindException.class)
  public ResponseEntity<ProblemDetails> handleMethodArgumentNotValidException(BindException exception) {
    List<ProblemDetails.Error> problemErrors = exception.getBindingResult().getAllErrors().stream()
      .map(objectError -> {
        String name = objectError.getObjectName();
        if (objectError instanceof FieldError) {
          name = ((FieldError) objectError).getField();
        }

        String message = this.messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
        return new ProblemDetails.Error()
          .setName(name)
          .setMessage(message);
      })
      .collect(Collectors.toList());

    String defaultMessage = "Some fields are invalid!";
    ProblemDetails problem = this.buildProblemDetails(HttpStatus.BAD_REQUEST, defaultMessage)
      .setErrors(problemErrors);

    return new ResponseEntity<>(problem, this.getDefaultHeaders(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({ InvalidRefreshTokenException.class, IllegalArgumentException.class})
  public ResponseEntity<ProblemDetails> handleInvalidRefreshTokenException(RuntimeException exception) {
    ProblemDetails problem = this.buildProblemDetails(HttpStatus.BAD_REQUEST, exception.getMessage());
    return new ResponseEntity<>(problem, this.getDefaultHeaders(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  public ResponseEntity<ProblemDetails> handleHttpMediaTypeNotSupportedException(Exception exception) {
    ProblemDetails problem = this.buildProblemDetails(HttpStatus.UNSUPPORTED_MEDIA_TYPE, exception.getMessage());
    return new ResponseEntity<>(problem, this.getDefaultHeaders(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
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

  @ExceptionHandler(InvalidCredentialsException.class)
  public ResponseEntity<ProblemDetails> handleInvalidCredentialsException(InvalidCredentialsException exception) {
    ProblemDetails problem = this.buildProblemDetails(HttpStatus.UNAUTHORIZED, exception.getMessage());
    return new ResponseEntity<>(problem, this.getDefaultHeaders(), HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(UserAlreadyExistsException.class)
  public ResponseEntity<ProblemDetails> handleUserAlreadyExistsException(UserAlreadyExistsException exception) {
    ProblemDetails problem = this.buildProblemDetails(HttpStatus.CONFLICT, exception.getMessage());
    return new ResponseEntity<>(problem, this.getDefaultHeaders(), HttpStatus.CONFLICT);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ProblemDetails> handleException(Exception exception) {
    exception.printStackTrace();
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
