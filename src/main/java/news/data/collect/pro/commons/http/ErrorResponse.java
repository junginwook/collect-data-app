package news.data.collect.pro.commons.http;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor
public class ErrorResponse {

    private final boolean success = false;

    private final int status;

    private final String message;

    @JsonInclude(JsonInclude.Include.NON_EMPTY) //errors 가 없다면 응답으로 내려가지 않도록
    private final List<ValidationError> errors;

    @Getter
    @Builder
    @RequiredArgsConstructor
    public static class ValidationError {

        private final String filed;
        private final String message;

        public static ValidationError of(final FieldError fieldError) {
            return ValidationError.builder()
                    .filed(fieldError.getField())
                    .message(fieldError.getDefaultMessage())
                    .build();
        }

    }
}
