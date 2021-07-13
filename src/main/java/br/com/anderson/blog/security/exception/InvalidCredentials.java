package br.com.anderson.blog.security.exception;

public class InvalidCredentials extends RuntimeException {

  public InvalidCredentials(String message) {
    super(message);
  }

}
