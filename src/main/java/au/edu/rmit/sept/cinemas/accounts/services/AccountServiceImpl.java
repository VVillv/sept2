package au.edu.rmit.sept.cinemas.accounts.services;

import au.edu.rmit.sept.cinemas.accounts.models.Account;
import au.edu.rmit.sept.cinemas.accounts.repositories.AccountRepository;
import java.util.Collection;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository repository;

    @Autowired
    public AccountServiceImpl(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public Collection<Account> getAccounts() {
        return repository.findAll();
    }

    @Override
    public Optional<Account> getAccount(Long id) {
        return repository.findById(id);
    }

    @Override
    public Account createAccount(Account movie) {
        return this.repository.create(movie);
    }

    @Override
    public void deleteAccountById(Long id) {
        this.repository.deleteById(id);
    }
}
