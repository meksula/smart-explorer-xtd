package pl.smartexplorer.scribe.exception;

/**
 * @author
 * Karol Meksuła
 * 20-10-2018
 * */

public class ScribeException extends RuntimeException {
    private String message;

    public ScribeException(final String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

}
