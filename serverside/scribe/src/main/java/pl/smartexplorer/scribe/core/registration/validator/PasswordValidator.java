package pl.smartexplorer.scribe.core.registration.validator;

/**
 * @author
 * Karol Meksuła
 * 03-11-2018
 * */

public class PasswordValidator {

    public static boolean validPassword(String password, String repeatedPassword) {
        return password.equals(repeatedPassword);
    }

}
