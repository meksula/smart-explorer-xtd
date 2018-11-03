package pl.smartexplorer.scribe.core.registration.builders;

/**
 * @author
 * Karol Meksuła
 * 03-11-2018
 *
 * This interface define what object we need to prepare to send to database microservice.
 * This object must build T object from delievered data.
 * */

public interface RegistrationBuilder<T, Data> {
    T mapToCerberRequiredObject(Data argument);
}
