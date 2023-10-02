package au.edu.rmit.sept.cinemas.accounts.controllers;
import au.edu.rmit.sept.cinemas.accounts.models.Account;
import au.edu.rmit.sept.cinemas.accounts.services.AccountService;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "v1/accounts")
public class AccountController {

    private AccountService service;

    @Autowired
    public AccountController(AccountService service) {
        this.service = service;
    }

    @GetMapping
    public Collection<Account> all() {

        return service.getAccounts();
    }

    @GetMapping("/{id}")
    public Account get(@PathVariable Long id) {
        return service.getAccount(id).orElseThrow(
                () -> new AccountNotFoundException(id));
    }

    @PostMapping
    public ResponseEntity<Account> newAccount(@RequestBody Account movie) {
        Account m = service.createAccount(movie);
        return new ResponseEntity<Account>(m, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAccount(@PathVariable Long id) {
        this.service.deleteAccountById(id);
        return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
    }
}
