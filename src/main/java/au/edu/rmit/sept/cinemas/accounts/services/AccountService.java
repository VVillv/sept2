package au.edu.rmit.sept.cinemas.accounts.services;

import au.edu.rmit.sept.cinemas.accounts.models.Account;
import java.util.Collection;
import java.util.Optional;

public interface AccountService {
    public Collection<Account> getAccounts();

    public Optional<Account> getAccount(Long id);

    public Account createAccount(Account movie);

    public void deleteAccountById(Long id);
}
