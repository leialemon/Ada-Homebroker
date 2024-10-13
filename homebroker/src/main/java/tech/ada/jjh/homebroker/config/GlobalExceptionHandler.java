package tech.ada.jjh.homebroker.config;

import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tech.ada.jjh.homebroker.exceptions.EntityNotFoundException;
import tech.ada.jjh.homebroker.exceptions.IncorrectPassword;
import tech.ada.jjh.homebroker.exceptions.IsAMinorException;
import tech.ada.jjh.homebroker.exceptions.NotEnoughFundsException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();


        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JdbcSQLIntegrityConstraintViolationException.class)
    public ResponseEntity<String> handleUniqueIndex(JdbcSQLIntegrityConstraintViolationException error) {
        return new ResponseEntity<>("Entidade já está cadastrada no banco de dados", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<String> handleUniqueIndexGenerico(JdbcSQLIntegrityConstraintViolationException error) {
        return new ResponseEntity<>("Entidade já está cadastrada no banco de dados", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFound(EntityNotFoundException error){
        return new ResponseEntity<>("Entidade não encontrada no banco de dados.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotEnoughFundsException.class)
    public ResponseEntity<String> handleInsufficientFunds(NotEnoughFundsException error){
        return new ResponseEntity<>(error.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IsAMinorException.class)
    public ResponseEntity<String> handleInsufficientAge(IsAMinorException error){
        return new ResponseEntity<>("O usuário precisa ter no mínimo 18 anos para usar o Homebroker.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IncorrectPassword.class)
    public ResponseEntity<String> handleIncorrectPassword(IncorrectPassword error){
        return new ResponseEntity<>(error.getMessage(), HttpStatus.UNAUTHORIZED);
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<String> handleUniqueIndexGenerico(Exception error) {
//        return new ResponseEntity<>("Você deve ter feito algo errado!.", HttpStatus.BAD_REQUEST);
//    }
}
