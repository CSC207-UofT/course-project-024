import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class LoginSystemTest {

    LoginSystem loginSystem;

    @BeforeEach
    void setUp() {
        loginSystem = new LoginSystem();

        Account account1 = AccountInteractor.createAccount("user1", "pass1");
        Account account2 = AccountInteractor.createAccount("user2", "pass2");
        loginSystem.accounts = new ArrayList<>(
                List.of(account1, account2)
        );
    }

    @Test
    void isValidLogin_ValidPassword() {
        assertTrue(loginSystem.isValidLogin("user1", "pass1"));
    }

    @Test
    void isValidLogin_InvalidPassword() {
        assertTrue(loginSystem.isValidLogin("user1", ""));
    }
}
