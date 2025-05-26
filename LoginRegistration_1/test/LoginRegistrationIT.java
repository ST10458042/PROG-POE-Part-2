import org.junit.Test;
import static org.junit.Assert.*;

public class LoginRegistrationIT {

    @Test
    public void testIsValidUsername() {
        assertTrue(LoginRegistration.isValidUsername("abc_d"));
        assertFalse(LoginRegistration.isValidUsername("abcd"));  // too short and no underscore
        assertFalse(LoginRegistration.isValidUsername("abcdef")); // too long
    }

    @Test
    public void testIsValidPassword() {
        assertTrue(LoginRegistration.isValidPassword("Passw0rd!"));
        assertFalse(LoginRegistration.isValidPassword("password")); // no caps, numbers, or symbols
        assertFalse(LoginRegistration.isValidPassword("PASS1234")); // no symbol
    }

    @Test
    public void testIsValidCell() {
        assertTrue(LoginRegistration.isValidCell("+27812345678"));
        assertFalse(LoginRegistration.isValidCell("0812345678")); // missing +27
        assertFalse(LoginRegistration.isValidCell("+27812"));     // too short
    }

    @Test
    public void testRegisterUser() {
        String result = LoginRegistration.registerUser("abc_d", "Passw0rd!", "+27812345678");
        assertTrue(result.contains("Username successfully recorded"));
        assertTrue(result.contains("Password successfully recorded"));
        assertTrue(result.contains("Cell phone number successfully recorded"));
    }

    @Test
    public void testLoginSuccess() {
        LoginRegistration.registerUser("abc_d", "Passw0rd!", "+27812345678");
        assertTrue(LoginRegistration.login("abc_d", "Passw0rd!"));
    }

    @Test
    public void testLoginFailure() {
        LoginRegistration.registerUser("abc_d", "Passw0rd!", "+27812345678");
        assertFalse(LoginRegistration.login("wrong", "Passw0rd!"));
        assertFalse(LoginRegistration.login("abc_d", "wrongPass"));
    }

    @Test
    public void testGetLoginStatusMessage() {
        LoginRegistration.registerUser("abc_d", "Passw0rd!", "+27812345678");
        String successMessage = LoginRegistration.getLoginStatusMessage("abc_d", "Passw0rd!");
        assertTrue(successMessage.contains("successfully logged in"));

        String failMessage = LoginRegistration.getLoginStatusMessage("wrong", "Passw0rd!");
        assertTrue(failMessage.contains("Login failed"));
    }

    // Skip testing the main() method. It's for manual interaction.
}
