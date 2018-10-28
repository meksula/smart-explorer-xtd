package pl.smartexplorer.sev2Token.core.matcher;

import java.nio.charset.Charset;
import java.util.Base64;

/**
 * @author
 * Karol Meksuła
 * 28-10-2018
 * */

public class RetrieveTokenData {

    public String extractParameter(final String sev2tokenEncoded, TokenParams tokenParam) {
        String decoded = decodeTokenToJson(sev2tokenEncoded);
        String[] params = decoded.split("\\+");

        return params[tokenParam.getParamIndex()].substring(tokenParam.substringIndex());
    }

    private String decodeTokenToJson(String sev2tokenEncoded) {
        byte[] bytes = Base64.getDecoder().decode(sev2tokenEncoded);
        return new String(bytes, Charset.forName("UTF-8"));
    }

    public enum TokenParams {
        USER_ID {
            @Override
            protected int getParamIndex() {
                return 0;
            }

            @Override
            protected int substringIndex() {
                return 1;
            }
        },
        USERNAME {
            @Override
            protected int getParamIndex() {
                return 1;
            }

            @Override
            protected int substringIndex() {
                return 0;
            }
        };

        protected abstract int getParamIndex();

        protected abstract int substringIndex();
    }

}
