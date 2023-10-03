package au.edu.rmit.sept.cinemas.accounts.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import au.edu.rmit.sept.cinemas.accounts.AccountsApplication;
import au.edu.rmit.sept.cinemas.accounts.models.Account;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = AccountsApplication.class)
@AutoConfigureMockMvc
public class AccountControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Flyway flyway;

    @BeforeEach
    public void setUp() {
        flyway.migrate(); // Ensure the database is in a clean state before each test
    }

    @AfterEach
    public void tearDown() {
        flyway.clean(); // Clean the database after each test
    }

    @Test
    public void testAllAccounts() throws Exception {
        mockMvc.perform(get("/v1/accounts"))
                .andExpect(status().isOk());
    }

    @Test
    void all_accounts() throws Exception {
        mockMvc.perform(get("/v1/accounts").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].owner", is("Alex")));
    }

    @Test
    void get_accounts_id_2() throws Exception {
        mockMvc.perform(get("/v1/accounts/2").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.owner", is("John")));
    }

    @Test
    void get_accounts_not_found() throws Exception {
        mockMvc.perform(get("/v1/accounts/22").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.title", is("Not Found")));
    }

    @Test
    void delete_accounts() throws Exception {
        mockMvc.perform(delete("/v1/accounts/{id}", 1)).andExpect(status().isAccepted());
    }
}
