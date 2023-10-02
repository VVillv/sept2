package au.edu.rmit.sept.cinemas.accounts.repositories;

import au.edu.rmit.sept.cinemas.accounts.models.Account;
import java.util.List;
import java.util.Optional;

public interface AccountRepository {
    public List<Account> findAll();

    public Optional<Account> findById(Long id);

    public Account create(Account account);

    public void deleteById(Long i);
}
