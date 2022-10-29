package news.data.collect.pro.commons.http;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class ApiResponse<T> {

    private final T result;

    private final boolean success;

    private final int status;

    private final String message;

}
