package yongkinanum.backenddeploy.core.error.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import yongkinanum.backenddeploy.core.utils.ApiUtils;

@Getter
public class Exception409 extends RuntimeException {

    public Exception409(String message) {
        super(message);
    }

    public ApiUtils.ApiResult<?> body(){
        return ApiUtils.error(getMessage(), HttpStatus.CONFLICT);
    }

    public HttpStatus status(){
        return HttpStatus.CONFLICT;
    }
}
