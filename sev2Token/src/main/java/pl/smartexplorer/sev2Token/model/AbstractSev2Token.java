package pl.smartexplorer.sev2Token.model;

/**
 * @author
 * Karol Meksuła
 * 12-10-2018
 * */

public abstract class AbstractSev2Token {
    private String userId;
    private String username;

    public AbstractSev2Token(String userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

}
