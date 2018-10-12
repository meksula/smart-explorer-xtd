package pl.smartexplorer.sev2Token.core.generator;

import pl.smartexplorer.sev2Token.exception.Sev2TokenException;
import pl.smartexplorer.sev2Token.model.AbstractSev2Token;
import pl.smartexplorer.sev2Token.model.Sev2TokenType;
import pl.smartexplorer.sev2Token.model.expirable.Sev2TokenExpirable;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author
 * Karol Meksuła
 * 12-10-2018
 * */

public class ExpirableTokenGenerator implements TokenGenerator {

    @Override
    public AbstractSev2Token generateToken(String userId, String username) {
        Sev2TokenExpirable sev2Token = new Sev2TokenExpirable(userId, username);
        sev2Token.setDate(LocalDateTime.now());
        sev2Token.setExpired(false);
        sev2Token.setSev2Uiid(UUID.randomUUID());
        sev2Token.setTokenType(Sev2TokenType.EXPIRABLE);
        return sev2Token;
    }

    @Override
    public String encodeToken(AbstractSev2Token token) {
        byte[] tokenBytes = writeTokenAsString((Sev2TokenExpirable) token).getBytes();
        return Base64.getEncoder().encodeToString(tokenBytes);
    }

    private String writeTokenAsString(Sev2TokenExpirable token) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(token.getUserId());
        stringBuilder.append("+");
        stringBuilder.append(token.getUsername());
        stringBuilder.append("+");
        stringBuilder.append(token.getTokenType().name());
        stringBuilder.append("+");
        stringBuilder.append(token.isExpired());
        stringBuilder.append("+");
        stringBuilder.append(token.getDate().toString());
        stringBuilder.append("+");
        stringBuilder.append(token.getSev2Uiid().toString());
        stringBuilder.append("}");

        String tokenAsString = stringBuilder.toString();
        if (regexCompatible(tokenAsString)) {
            return tokenAsString;
        } else {
            throw new Sev2TokenException("Cannot prepare Sev2Token. Some properties may be empty.");
        }

    }

    private boolean regexCompatible(String tokenAsString) {
        Pattern regex = Pattern.compile("\\{{1}[0-9]+\\+[a-zA-Z0-9.-=]+\\+[A-Z]+\\+[falsetrue]+\\+[-.:T0-9]+\\+" +
                "[-0-9a-zA-Z]+[}]{1}");
        Matcher matcher = regex.matcher(tokenAsString);
        return matcher.matches();
    }

}
