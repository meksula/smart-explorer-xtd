package pl.smartexplorer.cerber.exception;

/**
 * @author
 * Karol Meksuła
 * 24-10-2018
 * */

public class SmartExplorerRepositoryException extends RuntimeException {
    private String message;

    public SmartExplorerRepositoryException(final String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

}
