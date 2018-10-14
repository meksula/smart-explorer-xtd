package pl.smartexplorer.sev2Token.model;

import pl.smartexplorer.sev2Token.model.expirable.Sev2TokenExpirable;

/**
 * @author
 * Karol Meksuła
 * 12-10-2018
 * */

public enum Sev2TokenType {
    EXPIRABLE {
        @Override
        public Class<?> getTokenClass() {
            return Sev2TokenExpirable.class;
        }
    };

    public abstract Class<?> getTokenClass();
}
