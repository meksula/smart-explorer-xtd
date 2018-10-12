package pl.smartexplorer.sev2Token.exception;

/**
 * @author
 * Karol Meksuła
 * 12-10-2018
 * */

public class Sev2TokenException extends RuntimeException {
    private String message;

    public Sev2TokenException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

}
