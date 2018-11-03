package pl.smartexplorer.scribe.core.registration.builders;

import pl.smartexplorer.scribe.core.registration.dto.Registration;
import pl.smartexplorer.scribe.model.User;

/**
 * @author
 * Karol Meksuła
 * 03-11-2018
 * */

public enum RegistrationBuilderTypes {
    PRIMARY {
        @Override
        public RegistrationBuilder<User, Registration> produce() {
            return new PrimaryRegistrationBuilder();
        }
    };

    public abstract RegistrationBuilder<?,?> produce();
}
