package yongkinanum.backenddeploy.user;

import lombok.Getter;

@Getter
public enum Role {
    ROLE_ADMIN("관리자"),
    ROLE_USER("회원"),
    ROLE_SHOPPER("가게사장"),
    ROLE_DELIVERYMAN("배달기사");

    private String description;

    Role(String description) {
        this.description = description;
    }
}
