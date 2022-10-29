package news.data.collect.pro.commons.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import news.data.collect.pro.commons.enums.ErrorCode;

@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException{

    private final ErrorCode errorCode;

}
