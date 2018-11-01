package pl.smartexplorer.scribe.exception;

/**
 * @author
 * Karol Meksuła
 * 20-10-2018
 * */

public class ScribeAuthenticationException extends RuntimeException {
    private String message;

    public ScribeAuthenticationException(final String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

}
