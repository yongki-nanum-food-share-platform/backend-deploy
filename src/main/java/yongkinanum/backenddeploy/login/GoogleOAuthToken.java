package yongkinanum.backenddeploy.login;

import lombok.Data;

@Data
public class GoogleOAuthToken {
    private String access_token;
    private String token_type;
    private String id_token;
    private String refresh_token;
    private int expires_in;
    private String scope;
}
