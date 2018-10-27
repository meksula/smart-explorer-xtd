package pl.smartexplorer.cerber.exception;

/**
 * @author
 * Karol Meksuła
 * 24-10-2018
 * */

public class SmartExplorerRepositoryException extends RuntimeException {
    public static String message;

    public SmartExplorerRepositoryException(final String message) {
        SmartExplorerRepositoryException.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
