package yongkinanum.backenddeploy.user;

import lombok.Getter;

@Getter
public enum Role {
    ROLE_ADMIN("ADMIN"),
    ROLE_USER("USER"),
    ROLE_SHOPPER("SHOPPER"),
    ROLE_RIDER("RIDER");

    private String description;

    Role(String description) {
        this.description = description;
    }
}
