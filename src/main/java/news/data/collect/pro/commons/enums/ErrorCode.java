package news.data.collect.pro.commons.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.*;

@Getter
public enum ErrorCode {

    /** 400 */
    BAD_ARGUMENT(BAD_REQUEST, 400001, "bad argument"),
    INVALID_PARAMETER(BAD_REQUEST, 400002, "bad argument"),

    /** 401 */
    MEMBER_UNAUTHORIZED(UNAUTHORIZED, 400001, "unauthorized member"),
    CLIENT_USER_NOT_AUTHORIZED(UNAUTHORIZED, 400002, "client user not authorized"),

    /** 403 */
    FORBIDDEN_RESOURCE(FORBIDDEN, 403001, "forbidden resource"),

    /** 404 */
    USER_NOT_FOUND(NOT_FOUND, 404001, "user not found"),
    RESOURCE_NOT_FOUND(NOT_FOUND, 404002, "resource not found"),
    CLIENT_USER_NOT_FOUND(NOT_FOUND, 404003, "client user not found"),

    /** 409 */
    USER_ALREADY_EXIST(CONFLICT, 409001, "user already exist"),
    RESOURCE_ALREADY_EXIST(CONFLICT, 409002, "resource already exist"),
    RESOURCE_NAME_ALREADY_EXIST(CONFLICT, 409003, "resource name already exist"),

    /** 500 */
    INTERNAL_SERVER_ERR(INTERNAL_SERVER_ERROR, 500001, "internal server error");

    private final HttpStatus httpStatus;
    private final int  status;
    private final String message;

    ErrorCode(HttpStatus httpStatus, int status, String message) {
        this.httpStatus = httpStatus;
        this.status = status;
        this.message = message;
    }
}
