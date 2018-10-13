package pl.smartexplorer.sev2Token.model.expirable;

import pl.smartexplorer.sev2Token.model.AbstractSev2Token;
import pl.smartexplorer.sev2Token.model.Sev2TokenType;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author
 * Karol Meksuła
 * 12-10-2018
 * */

public class Sev2TokenExpirable extends AbstractSev2Token {
    private Sev2TokenType tokenType;
    private UUID sev2Uiid;
    private LocalDateTime date;
    private boolean isExpired;

    public Sev2TokenExpirable(String userId, String username) {
        super(userId, username);
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;
    }

    public void setSev2Uiid(UUID sev2Uiid) {
        this.sev2Uiid = sev2Uiid;
    }

    public void setTokenType(Sev2TokenType tokenType) {
        this.tokenType = tokenType;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Sev2TokenType getTokenType() {
        return tokenType;
    }

    public UUID getSev2Uiid() {
        return sev2Uiid;
    }

    public boolean isExpired() {
        return isExpired;
    }

    @Override
    public int hashCode() {
        return date.hashCode() + sev2Uiid.hashCode() + tokenType.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        Sev2TokenExpirable otherToken = (Sev2TokenExpirable) obj;
        return this.sev2Uiid.equals(otherToken.getSev2Uiid())
                && this.date.equals(otherToken.getDate())
                && this.getUserId().equals(otherToken.getUserId());
    }

    @Override
    public String toString() {
        return "Type: " + tokenType.name()
                + ",\nDate : " + date.toString()
                + ",\nUserID: " + this.getUserId();
    }

}
