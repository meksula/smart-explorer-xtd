package pl.smartexplorer.cerber.services.registration;

/**
 * @author
 * Karol Meksuła
 * 01-11-2018
 * */

public interface RegistrationVerificator {
    boolean verifyRegistration(String userId, String verificationUuid);
}
