package yongkinanum.backenddeploy.login;

import lombok.Data;

@Data
public class NaverOAuthToken {
    private String access_token;
    private String token_type;
    private String refresh_token;
    private int expires_in;
    private String error;
    private String error_description;
}

