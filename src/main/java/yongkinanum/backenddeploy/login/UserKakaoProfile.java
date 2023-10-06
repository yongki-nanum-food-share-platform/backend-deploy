package yongkinanum.backenddeploy.login;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserKakaoProfile {
    public Long id;
    public String connected_at;
}