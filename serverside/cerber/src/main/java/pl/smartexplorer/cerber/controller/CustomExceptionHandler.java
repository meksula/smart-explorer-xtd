package pl.smartexplorer.cerber.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.smartexplorer.cerber.exception.SmartExplorerRepositoryException;
import pl.smartexplorer.cerber.exception.TokenUpdateException;

/**
 * @author
 * Karol Meksuła
 * 22-10-2018
 * */

@RestControllerAdvice
public class CustomExceptionHandler {

    /*@ExceptionHandler(TokenUpdateException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String tokenCannotBeUpdate() {
        return TokenUpdateException.message;
    }

    @ExceptionHandler(SmartExplorerRepositoryException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String cannotUpdateBecauseJustExist() {
        return SmartExplorerRepositoryException.message;
    }*/

}
