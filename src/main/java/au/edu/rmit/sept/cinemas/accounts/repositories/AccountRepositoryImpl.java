package au.edu.rmit.sept.cinemas.accounts.repositories;

import au.edu.rmit.sept.cinemas.accounts.models.Account;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;

@Repository
public class AccountRepositoryImpl implements AccountRepository {

    private final DataSource source;

    public AccountRepositoryImpl(DataSource source) {
        this.source = source;
    }

    private Account extractAccount(ResultSet rs) throws SQLException {
        return new Account(rs.getLong(1), rs.getString(2), rs.getInt(3),rs.getInt(4));
    }

    @Override
    public List<Account> findAll() {
        try {
            Connection connection = this.source.getConnection();
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM accounts;");
            List<Account> accounts = new ArrayList<>();
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Account m = extractAccount(rs);
                accounts.add(m);
            }
            connection.close();
            return accounts;
        } catch (SQLException e) {
            throw new RuntimeException("Error in findAll", e);
        }
    }

    public Optional<Account> findById(Long id) {
        try {
            PreparedStatement stm = this.source.getConnection().prepareStatement(
                    "SELECT * FROM accounts WHERE id = ?");
            stm.setLong(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return Optional.of(extractAccount(rs));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Error in findById", e);
        }
    }

    @Override
    public Account create(Account account) {
        try {
            PreparedStatement stm = this.source.getConnection().prepareStatement(
                    "INSERT INTO accounts (owner, number, balance) VALUES (?, ?,?)",
                    Statement.RETURN_GENERATED_KEYS);

            stm.setString(1, account.owner());
            stm.setInt(2, account.number());
            stm.setInt(3, account.balance());
            int row = stm.executeUpdate();

            if (row == 0) {
                throw new SQLException("Failed to create Account = " + account.toString());
            }

            ResultSet generatedKeys = stm.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getLong(1);
                return new Account(id, account.owner(), account.number(), account.balance());
            } else {
                throw new SQLException("Creating account failed, no ID obtained.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error in create", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            PreparedStatement stm = this.source.getConnection().prepareStatement(
                    "DELETE FROM accounts WHERE id=?", Statement.RETURN_GENERATED_KEYS);

            stm.setLong(1, id);

            int row = stm.executeUpdate();

            if (row == 0) {
                throw new SQLException("Failed to delete account ID = " + id);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error in create", e);
        }
    }
}
