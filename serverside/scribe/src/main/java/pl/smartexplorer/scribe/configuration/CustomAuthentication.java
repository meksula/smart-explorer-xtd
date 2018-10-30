package pl.smartexplorer.scribe.configuration;

import org.springframework.security.core.Authentication;

/**
 * @author
 * Karol Meksuła
 * 30-10-2018
 * */

public interface CustomAuthentication extends Authentication {
    String getSev2token();
}
