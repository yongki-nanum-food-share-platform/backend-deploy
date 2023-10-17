package yongkinanum.backenddeploy.login;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserNaverProfile {
    private Response response;

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Response {
        private String nickname;
        private String email;
    }
}
