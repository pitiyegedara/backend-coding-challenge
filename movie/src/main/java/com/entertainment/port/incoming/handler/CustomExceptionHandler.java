package com.entertainment.port.incoming.handler;

import com.entertainment.domain.movie.exception.MovieCreateException;
import com.entertainment.domain.movie.exception.MovieNotFoundException;
import com.entertainment.domain.movie.exception.RateCreateException;
import com.entertainment.domain.user.exception.UserAlreadyExistException;
import com.entertainment.domain.user.exception.UserCreationException;
import com.entertainment.domain.user.exception.UserNotFoundException;
import com.entertainment.movie.dto.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Arrays;
import java.util.Objects;

@RestControllerAdvice
public class CustomExceptionHandler {

    /**
     * Handles the exceptions that occurs due to invalid method parameters
     *
     * @param ex an instance of MethodArgumentNotValidException
     * @return ResponseEntity<CommonResponse> formatted response with bad request status
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse> handleBadRequestException(MethodArgumentNotValidException ex) {
        CommonResponse commonResponse = new CommonResponse().message("");
        Arrays.stream(Objects.requireNonNull(ex.getDetailMessageArguments())).forEach(message -> {
            if (null != message && !message.toString().isEmpty()) {
                commonResponse.message(commonResponse.getMessage().concat(message.toString()));
            }
        });

        return new ResponseEntity<>(commonResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles the object not found exceptions
     *
     * @param ex an instance of Exception
     * @return ResponseEntity<CommonResponse> formatted response with Not found status
     */
    @ExceptionHandler({MovieNotFoundException.class, UserNotFoundException.class})
    public ResponseEntity<CommonResponse> handleNotFoundExceptions(Exception ex) {
        return new ResponseEntity<>(new CommonResponse().message(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles custom validation exceptions
     *
     * @param ex an instance of Exception
     * @return ResponseEntity<CommonResponse> formatted response with already reported status
     */
    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<CommonResponse> handleAlreadyExistExceptions(Exception ex) {
        return new ResponseEntity<>(new CommonResponse().message(ex.getMessage()), HttpStatus.ALREADY_REPORTED);
    }

    /**
     * Handles system error cases
     *
     * @param ex an instance of Exception
     * @return ResponseEntity<CommonResponse> formatted response with internal server error status
     */
    @ExceptionHandler({MovieCreateException.class, RateCreateException.class, UserCreationException.class})
    public ResponseEntity<CommonResponse> handleInternalExceptions(Exception ex) {
        return new ResponseEntity<>(new CommonResponse().message(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles method argument mismatch exceptions
     *
     * @param ex exception instance
     * @return ResponseEntity<CommonResponse> formatted response with bad request exception
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<CommonResponse> handleBadRequestException(MethodArgumentTypeMismatchException ex) {
        CommonResponse commonResponse = new CommonResponse()
                .message("parameter `" + ex.getPropertyName() + "` is invalid");
        return new ResponseEntity<>(commonResponse, HttpStatus.BAD_REQUEST);
    }

}
