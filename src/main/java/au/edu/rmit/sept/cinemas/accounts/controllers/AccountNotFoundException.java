package au.edu.rmit.sept.cinemas.accounts.controllers;

public class AccountNotFoundException extends RuntimeException {
  public AccountNotFoundException(Long id) {
    super("Could not find Account with ID = " + id);
  }
}
