package au.edu.rmit.sept.cinemas.accounts.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class AccountNotFoundAdvice {

  @ResponseBody
  @ExceptionHandler(AccountNotFoundException.class)
  ResponseStatusException handleAccountNotFound(AccountNotFoundException ex) {
    return new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(),
        ex);
  }
}
