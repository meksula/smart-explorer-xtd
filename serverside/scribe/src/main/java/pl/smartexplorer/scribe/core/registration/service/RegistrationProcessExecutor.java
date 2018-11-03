package pl.smartexplorer.scribe.core.registration.service;

/**
 * @author
 * Karol Meksuła
 * 03-11-2018
 *
 * This is main interface of registration process. This object should connect with
 * Cerber and push mail message to message broker or other Queue
 * */

public abstract class RegistrationProcessExecutor<T, D> {
    public abstract T execute(D data);

    protected abstract T databaseServiceRequest(Object requestBody);

    protected abstract boolean putMessageToBroker(T t);
}
