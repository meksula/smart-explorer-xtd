package pl.smartexplorer.cerber.exception;

/**
 * @author
 * Karol Meksuła
 * 26-10-2018
 * */

public class TokenUpdateException extends RuntimeException {
    public static String message;

    public TokenUpdateException(String message) {
        TokenUpdateException.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
