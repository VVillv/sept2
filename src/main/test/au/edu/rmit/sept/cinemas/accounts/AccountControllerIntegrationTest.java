import au.edu.rmit.sept.cinemas.accounts.controllers.AccountController;
import au.edu.rmit.sept.cinemas.accounts.models.Account;
import au.edu.rmit.sept.cinemas.accounts.services.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Test
    public void testGetAllAccounts() throws Exception {
        mockMvc.perform(get("/v1/accounts"))
               .andExpect(status().isOk());
        verify(accountService, times(1)).getAccounts();
    }

    @Test
    public void testGetAccountById() throws Exception {
        Long accountId = 1L;
        when(accountService.getAccount(accountId)).thenReturn(java.util.Optional.of(new Account()));
        mockMvc.perform(get("/v1/accounts/" + accountId))
               .andExpect(status().isOk());
        verify(accountService, times(1)).getAccount(accountId);
    }

    // Similarly, you can add tests for POST and DELETE methods.
}
