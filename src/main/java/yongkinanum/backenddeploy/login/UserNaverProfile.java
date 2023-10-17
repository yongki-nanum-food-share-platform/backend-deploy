package yongkinanum.backenddeploy.login;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserNaverProfile {
    public String nickname;
    public String email;
}
