package br.com.anderson.blog.exceptions;

import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

public class CustomBindException extends BindException {

  public CustomBindException(BindingResult bindingResult) {
    super(bindingResult);
  }

}
