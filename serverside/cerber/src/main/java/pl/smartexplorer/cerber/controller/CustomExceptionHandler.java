package pl.smartexplorer.cerber.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.smartexplorer.cerber.exception.SmartExplorerRepositoryException;
import pl.smartexplorer.cerber.exception.TokenUpdateException;

import javax.persistence.EntityNotFoundException;

/**
 * @author
 * Karol Meksuła
 * 22-10-2018
 * */

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(TokenUpdateException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String tokenCannotBeUpdate() {
        return TokenUpdateException.message;
    }

    @ExceptionHandler(SmartExplorerRepositoryException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String cannotUpdateBecauseJustExist() {
        return SmartExplorerRepositoryException.message;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void cannotFindUser() {
        log.error("Cerber could not found user in database");
    }

}
