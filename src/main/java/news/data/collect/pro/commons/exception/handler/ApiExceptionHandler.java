package news.data.collect.pro.commons.exception.handler;

import news.data.collect.pro.commons.enums.ErrorCode;
import news.data.collect.pro.commons.exception.CustomException;
import news.data.collect.pro.commons.http.ApiResponse;
import news.data.collect.pro.commons.http.ErrorResponse;
import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApiExceptionHandler {

    /**
     * api 예외 처리
     */
    @ExceptionHandler(
            CustomException.class
    )
    public ResponseEntity<ErrorResponse> customException(CustomException e) {

        ErrorCode errorCode = e.getErrorCode();

        return handleExceptionInternal(errorCode);
    }

    private ResponseEntity<ErrorResponse> handleExceptionInternal(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(errorCode));
    }

    private ErrorResponse makeErrorResponse(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .status(errorCode.getStatus())
                .message(errorCode.getMessage())
                .build();
    }


    /**
     * validation 예외 처리
     */
    @ExceptionHandler(
            MethodArgumentNotValidException.class
    )
    public ResponseEntity<ErrorResponse> requestValidationException(MethodArgumentNotValidException e) {

        ErrorCode errorCode = ErrorCode.INVALID_PARAMETER;

        return handleExceptionInternal(e, errorCode);
    }

    private ResponseEntity<ErrorResponse> handleExceptionInternal(BindException e, ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(e, errorCode));
    }

    private ErrorResponse makeErrorResponse(BindException e, ErrorCode errorCode) {
        List<ErrorResponse.ValidationError> validationErrorList = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(ErrorResponse.ValidationError::of)
                .collect(Collectors.toList());

        return ErrorResponse.builder()
                .status(errorCode.getStatus())
                .message(errorCode.getMessage())
                .errors(validationErrorList)
                .build();
    }
}
